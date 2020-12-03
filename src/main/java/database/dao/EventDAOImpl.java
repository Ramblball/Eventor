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
 * Класс слой запросов к базе данных к таблице мероприятий
 */
public class EventDAOImpl extends DAO{

    /**
     * Метод отправляющий запрос на поиск мероприятий по уникальному идентификатору
     * @param id            Уникальный идентификатор мероприятия
     * @return              Объект мероприятия
     */
    public Event findById(int id) {
        try (Session session = openSession()) {
            return session.get(Event.class, id);
        }
    }

    /**
     * Метод отправляющий запрос на поиск по имени
     * @param name          Название мероприятия
     * @return              Объект мероприятия
     */
    public Event findByName(String name) {
        try (Session session = openSession()) {
            return session.createQuery("FROM Event WHERE name=:name", Event.class).setParameter(DBLiterals.NAME, name).getSingleResult();
        }
    }

    /**
     * Метод отправляющий запрос на создание меропритяия
     * @param user          Создатель
     * @param event         Мероприятие
     */
    public void create(User user, Event event) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.refresh(user);
            user.addCreatedEvent(event);
            session.save(event);
            transaction.commit();
            transaction.begin();
            session.createSQLQuery(DBLiterals.CREATE_EVENT_SET_VECTOR_QUERY)
                    .setParameter(DBLiterals.EVENT_ID, event.getId())
                    .setParameter(DBLiterals.DESCRIPTION, event.getDescription())
                    .executeUpdate();
            transaction.commit();
        }
    }

    /**
     * Метод отправляющий запрос на обновление мероприятия
     * @param event         Мероприятие
     */
    public void update(Event event) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(event);
            transaction.commit();
            transaction.begin();
            session.createSQLQuery(DBLiterals.UPDATE_EVENT_UPDATE_VECTOR_QUERY)
                    .setParameter(DBLiterals.DESCRIPTION, event.getDescription())
                    .setParameter(DBLiterals.EVENT_ID, event.getId())
                    .executeUpdate();
            transaction.commit();
        }
    }

    /**
     * Метод отправляющий запрос на удаление мероприятия
     * @param user          Создатель
     * @param event         Мероприятие
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
     * Метод отправляющий запрос на поиск  всех мероприятий
     * @return              Список мероприятий
     */
    public List<Event> findAll() {
        try (Session session = openSession()) {
            return session.createQuery("FROM Event", Event.class).getResultList();
        }
    }

    /**
     * Метод отправляющий запрос на поиск мероприятий по заданным критериям
     * @param query         Запрос с критериями поиска
     * @return              Найденные мероприятия
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DBLiterals.DATE_TIME_FORMAT);
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
     * Метод отправляющий запрос на подписку пользоватля на мероприятие
     * @param user          Пользователь
     * @param event         Мероприятие
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
     * Метод отправляющий запрос на отписку пользователя от мероприятия
     * @param user          Пользователь
     * @param event         Мероприятие
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