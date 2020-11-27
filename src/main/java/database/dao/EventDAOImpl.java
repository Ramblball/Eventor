package database.dao;

import database.DBLiterals;
import database.model.Category;
import database.model.Event;
import database.model.User;
import database.utils.EventQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventDAOImpl extends DAO{

    public Event findById(int id) {
        try (Session session = openSession()) {
            return session.get(Event.class, id);
        }
    }

    public Event findByName(String name) {
        try (Session session = openSession()) {
            return session.createQuery("FROM Event WHERE name=:name", Event.class).setParameter(DBLiterals.name, name).getSingleResult();
        }
    }

    public void create(User user, Event event) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.refresh(user);
            user.addCreatedEvent(event);
            session.save(event);
            transaction.commit();
            transaction.begin();
            session.createSQLQuery(DBLiterals.createEventSetVectorQuery)
                    .setParameter(DBLiterals.eventId, event.getId())
                    .setParameter(DBLiterals.description, event.getDescription())
                    .executeUpdate();
            transaction.commit();
        }
    }

    public void update(Event event) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(event);
            transaction.commit();
            transaction.begin();
            session.createSQLQuery(DBLiterals.updateEventUpdateVectorQuery)
                    .setParameter(DBLiterals.description, event.getDescription())
                    .setParameter(DBLiterals.eventId, event.getId())
                    .executeUpdate();
            transaction.commit();
        }
    }

    public void delete(User user, Event event) {
        try (Session session = openSession()) {
            session.refresh(user);
            user.removeCreatedEvent(event);
        }
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(event);
            transaction.commit();
        }
    }

    public List<Event> findAll() {
        try (Session session = openSession()) {
            return session.createQuery("FROM Event", Event.class).getResultList();
        }
    }

    public List<Event> find(EventQuery query) {
        try (Session session = openSession()) {
            if (query.isEmpty()) {
                return null;
            }
            List<Event> result = new ArrayList<>();
            session.getTransaction().begin();
            List<Object[]> rows = session.createSQLQuery(query.execute()).list();
            for (Object[] row : rows) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DBLiterals.dateTimeFormat);
                String time = row[3].toString().substring(0, 16);
                Event event = new Event(
                        row[1].toString(),
                        row[2].toString(),
                        LocalDateTime.parse(time, formatter),
                        Category.values()[Integer.parseInt(row[6].toString())],
                        row[4].toString()
                );
                event.setId(Integer.parseInt(row[0].toString()));
                event.setUserId(Integer.parseInt(row[5].toString()));
                result.add(event);
            }
            session.getTransaction().commit();
            return result;
        }
    }

    public void subscribe(User user, Event event) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.refresh(user);
            session.refresh(event);
            user.addSubscribe(event);
            transaction.commit();
        }
    }

    public void unsubscribe(User user, Event event) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.refresh(user);
            session.refresh(event);
            user.removeSubscribe(event);
            transaction.commit();
        }
    }
}
