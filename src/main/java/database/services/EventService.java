package database.services;

import database.exception.DBException;
import database.DBLiterals;
import database.dao.EventDAOImpl;
import database.exception.NotFoundException;
import database.model.Event;
import database.model.User;
import database.utils.EventQuery;
import org.hibernate.QueryParameterException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.hql.internal.ast.QuerySyntaxException;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Класс сервис для взаимодействия основного приложения с базой данных мероприятий
 */
public class EventService {
    private static final EventDAOImpl eventDAO = new EventDAOImpl();

    /**
     * Метод для получения мероприятия по названию
     * @param name                  Название мероприятия
     * @return                      Найденное мероприятие
     * @throws NotFoundException    Мероприятий не найдено
     * @throws QuerySyntaxException Ошибка синтаксиса запроса
     */
    public Event findByName(String name) throws NotFoundException, DBException {
        try {
            Event event = eventDAO.findByName(name);
            if (event == null) {
                throw new NotFoundException(DBLiterals.EVENT_NOT_FOUND);
            }
            return event;
        } catch (NoResultException e) {
            throw new NotFoundException(DBLiterals.EVENT_NOT_FOUND);
        } catch (QuerySyntaxException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод для создания мероприятия
     * @param event                 Новое мероприятие
     * @throws PersistenceException Ошибка сохранения
     */
    public void create(Event event) throws DBException{
        try {
            eventDAO.create(event);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод для обновления мероприятия
     * @param event                 Мероприятие
     * @throws PersistenceException Ошибка сохранения
     * @throws PersistenceException Ошибка сохранения
     */
    public void update(Event event) throws DBException{
        try {
            eventDAO.update(event);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод для удаления мероприятия
     * @param event                 Удаляемое мероприятие
     * @throws PersistenceException Ошибка сохранения
     */
    public void remove(Event event) throws DBException {
        try {
            eventDAO.remove(event);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод для удаления прошедших мероприятий
     * @return                      Количество удаленных мероприятий
     */
    public int removeCompletedEvents() throws DBException {
        try {
            return eventDAO.deleteCompletedEvents();
        } catch (NoResultException e) {
            return 0;
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод для получения мероприятий по параметрам, заданным в query
     * @param query                     EventQuery с параметрами поиска
     * @return                          Список найденных мероприятий
     * @throws NotFoundException        Мероприятий не найдено
     * @throws SQLGrammarException      Ошибка синтаксиса SQL запроса
     * @throws QueryParameterException  Попытка поиска без заданных параметров
     */
    public List<Event> findWithFilter(EventQuery query) throws NotFoundException, DBException {
        try {
            List<Event> events = eventDAO.find(query);
            if (events == null) {
                throw new NotFoundException(DBLiterals.EVENT_NOT_FOUND);
            }
            return events;
        } catch (SQLGrammarException | QueryParameterException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод для получения мероприятий, проходящих в заданном временном интервале
     * @param begin                     Время начала интервала (Включительно)
     * @param end                       Время конца интервала (Исключительно)
     * @return                          Список найденных мероприятий
     * @throws NotFoundException        Мероприятий не найдено
     * @throws QuerySyntaxException     Ошибка синтаксиса запроса
     */
    public List<Event> findWithInterval(LocalDateTime begin, LocalDateTime end) throws NotFoundException, DBException {
        try {
            List<Event> events = eventDAO.findByTimeInterval(begin, end);
            if (events == null) {
                throw new NotFoundException(DBLiterals.EVENT_NOT_FOUND);
            }
            return events;
        } catch (SQLGrammarException | QueryParameterException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод получения всех созданных мероприятий
     * @return list of all events
     */
    public List<Event> findAll() throws NotFoundException, DBException {
        try {
            List<Event> events = eventDAO.findAll();
            if (events == null) {
                throw new NotFoundException(DBLiterals.EVENT_NOT_FOUND);
            }
            return events;
        } catch (NoResultException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод для подписки пользователя на участие в мероприятии
     * @param user                  Пользователь
     * @param event                 Мероприятие
     * @throws PersistenceException Ошибка сохранения
     */
    public void subscribe(User user, Event event) throws DBException{
        try {
            Set<Event> createdEvents = user.getCreatedEvents();
            if (createdEvents.contains(event)) {
                throw new DBException(DBLiterals.USER_CREATOR);
            }
            Set<Event> subscribes = user.getSubscribes();
            if (subscribes.contains(event)) {
                throw new DBException(DBLiterals.USER_SUBSCRIBER);
            }
            Set<User> subscribers = event.getSubscribers();
            if (subscribers.size() == event.getLimit()) {
                throw new DBException(DBLiterals.LIMIT_ACHIEVED);
            }
            eventDAO.subscribe(user, event);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод отписки пользователя от мероприятия
     * @param user                  Пользователь
     * @param event                 Мероприятие
     * @throws PersistenceException Ошибка сохранения
     */
    public void unsubscribe(User user, Event event) throws DBException{
        try {
            eventDAO.unsubscribe(user, event);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }
}