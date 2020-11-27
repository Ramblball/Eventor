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

/**
 * Класс слой запросов к базе мероприятий
 */
public class EventDAOImpl extends DAO{

    /**
     * Запрос на поиск по id
     * @param id          Id мероприятия
     * @return            Объект мероприятия
     */
    public Event findById(int id) {
        try (Session session = openSession()) {
            return session.get(Event.class, id);
        }
    }

    /**
     * Запрос на поиск по имени
     * @param name        Имя мероприятия
     * @return            Объект мероприятия
     */
    public Event findByName(String name) {
        try (Session session = openSession()) {
            return session.createQuery("FROM Event WHERE name=:name", Event.class).setParameter(DBLiterals.name, name).getSingleResult();
        }
    }

    /**
     * Запрос на создание меропритяия
     * @param user        Создатель
     * @param event       Мероприятие
     */
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

    /**
     * Запрос на обновление мероприятия
     * @param event       Мероприятие
     */
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

    /**
     * Запрос на удаление мероприятия
     * @param user        Создатель
     * @param event       Мероприятие
     */
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

    /**
     * Запрос на поиск  всех мероприятий
     * @return            Список мероприятий
     */
    public List<Event> findAll() {
        try (Session session = openSession()) {
            return session.createQuery("FROM Event", Event.class).getResultList();
        }
    }

    /**
     * Запрос на поиск мероприятий по заданным критериям
     * @param query       Запрос с критериями поиска
     * @return            Найденные мероприятия
     */
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

    /**
     * Запрос на подписку пользоватля на мероприятие
     * @param user        Пользователь
     * @param event       Мероприятие
     */
    public void subscribe(User user, Event event) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.refresh(user);
            session.refresh(event);
            user.addSubscribe(event);
            transaction.commit();
        }
    }

    /**
     * Запрос на отписку пользователя от мероприятия
     * @param user        Пользователь
     * @param event       Мероприятие
     */
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