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
 * Класс запросов к базе данных к таблице мероприятий
 */
public class EventDAOImpl extends DAO{

    /**
     * Метод для отправки запроса на поиск по имени
     * @param name          Название мероприятия
     * @return              Объект мероприятия
     */
    public Event findByName(String name) {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.EVENT_WITH_SUBSCRIBERS);
            return session.createQuery("FROM Event WHERE name=:name AND time > :now", Event.class)
                    .setParameter("now", LocalDateTime.now())
                    .setParameter(DBLiterals.NAME, name)
                    .getSingleResult();
        }
    }

    /**
     * Метод для отправки запроса на создание мероприятия
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
     * Метод для отправки запроса на обновление мероприятия
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
     * Метод для отправки запроса на удаление мероприятия
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
     * Метод для отправки запроса на удаление прошедших мероприятий
     * @return              Количество удаленных мероприятий
     */
    public int deleteCompleted() {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.EVENT_WITH_SUBSCRIBERS);
            List<Event> events = session.createQuery("FROM Event WHERE time <= :now", Event.class)
                    .setParameter("now", LocalDateTime.now())
                    .getResultList();
            int count = events.size();
            session.getTransaction().begin();
            for (Event event : events) {
                session.delete(event);
            }
            session.getTransaction().commit();
            return count;
        }
    }

    /**
     * Метод для отправки запроса на поиск всех мероприятий
     * @return              Список мероприятий
     */
    public List<Event> findAll() {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.EVENT_WITH_SUBSCRIBERS);
            return session.createQuery("FROM Event WHERE time > :now AND limit > subscribers.size", Event.class)
                    .setParameter("now", LocalDateTime.now())
                    .getResultList();
        }
    }

    /**
     * Метод для отправки запроса на поиск мероприятий по заданным критериям
     * @param query         Запрос с критериями поиска
     * @return              Найденные мероприятия
     */
    public List<Event> find(EventQuery query) {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.EVENT_WITH_SUBSCRIBERS);
            if (query.isEmpty()) {
                return null;
            }
            List<Event> result = new ArrayList<>();
            session.getTransaction().begin();
            List<Object[]> rows = session.createSQLQuery(query.execute()).list();
            for (Object[] row : rows) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DBLiterals.DATE_TIME_FORMAT);
                String time = row[2].toString().substring(0, 16);
                Event event = new Event(
                        row[1].toString(),
                        row[9].toString(),
                        Float.parseFloat(row[7].toString()),
                        Float.parseFloat(row[8].toString()),
                        Integer.parseInt(row[6].toString()),
                        LocalDateTime.parse(time, formatter),
                        Category.values()[Integer.parseInt(row[5].toString())],
                        row[3].toString()
                );
                event.setId(Integer.parseInt(row[0].toString()));
                event.setUserId(Integer.parseInt(row[4].toString()));
                result.add(event);
            }
            session.getTransaction().commit();
            return result;
        }
    }

    /**
     * Метод для отправки запроса на подписку пользователя на мероприятие
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
     * Метод для отправки запроса на отписку пользователя от мероприятия
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

    /**
     * Метод для отправки запроса на поиск мероприятий в заданном временном интервале
     * @param begin         Время начала интервала (Включительно)
     * @param end           Время конца интервала (Исключительно)
     * @return              Найденные мероприятия
     */
    public List<Event> findByTimeInterval(LocalDateTime begin, LocalDateTime end) {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.EVENT_WITH_SUBSCRIBERS);
            return session.createQuery("FROM Event WHERE time >= :begin AND time < :end AND limit > subscribers.size", Event.class)
                    .setParameter("begin", begin)
                    .setParameter("end", end)
                    .getResultList();
        }
    }
}