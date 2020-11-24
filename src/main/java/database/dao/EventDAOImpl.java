package database.dao;

import database.DBLiterals;
import database.model.Category;
import database.model.Event;
import database.utils.EventQuery;
import org.hibernate.Session;

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
                Event event = new Event(
                        row[1].toString(),
                        row[2].toString(),
                        LocalDateTime.parse(row[3].toString(), DateTimeFormatter.ofPattern(DBLiterals.dateTimeFormat)),
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
}
