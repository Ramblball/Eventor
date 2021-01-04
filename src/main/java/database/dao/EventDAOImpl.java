package database.dao;

import database.DBLiterals;
import database.model.Category;
import database.model.Event;
import database.model.User;
import database.utils.EventQuery;
import database.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс запросов к базе данных к таблице мероприятий
 */
public class EventDAOImpl implements DAO<Event> {

    /**
     * Метод создания сессии работы с пользователем
     * @return            Сессия взаимодействия с базой данных
     */
    private Session openSession(){
        return HibernateSessionFactory.getSessionFactory().openSession();
    }

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
     * Метод для отправки запроса на поиск по уникальному идентификатору
     * @param id            Уникальный идентификатор мероприятия
     * @return              Объект мероприятия
     */
    @Override
    public Event findById(int id) {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.EVENT_WITH_SUBSCRIBERS);
            return session.get(Event.class, id);
        }
    }

    /**
     * Метод для отправки запроса на создание мероприятия
     * @param entity         Мероприятие
     */
    @Override
    public void create(Event entity) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            transaction.begin();
            session.createSQLQuery(DBLiterals.CREATE_EVENT_SET_VECTOR_QUERY)
                    .setParameter(DBLiterals.EVENT_ID, entity.getId())
                    .setParameter(DBLiterals.DESCRIPTION, entity.getDescription())
                    .executeUpdate();
            transaction.commit();
        }
    }

    /**
     * Метод для отправки запроса на обновление мероприятия
     * @param entity         Мероприятие
     */
    @Override
    public void update(Event entity) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            transaction.begin();
            session.createSQLQuery(DBLiterals.UPDATE_EVENT_UPDATE_VECTOR_QUERY)
                    .setParameter(DBLiterals.DESCRIPTION, entity.getDescription())
                    .setParameter(DBLiterals.EVENT_ID, entity.getId())
                    .executeUpdate();
            transaction.commit();
        }
    }

    /**
     * Метод для отправки запроса на удаление мероприятия
     * @param entity         Мероприятие
     */
    @Override
    public void remove(Event entity) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }

    /**
     * Метод для отправки запроса на удаление прошедших мероприятий
     * @return              Количество удаленных мероприятий
     */
    public int deleteCompletedEvents() {
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
                        Integer.parseInt(row[4].toString()),
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