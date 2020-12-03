package database.services;

import database.DBException;
import database.DBLiterals;
import database.dao.EventDAOImpl;
import database.model.Event;
import database.model.User;
import database.utils.EventQuery;
import org.hibernate.QueryParameterException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.hql.internal.ast.QuerySyntaxException;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Set;

/**
 * Слой сервис для взаимодействия основного приложения с базой данных мероприятий
 */
public class EventService {
    private static final EventDAOImpl eventDAO = new EventDAOImpl();

    /**
     * Возвращает мероприятие по id
     * или null при возникновении ошибки
     * @param id          Id мероприятия
     * @return            Найденное мероприятие
     */
    public Event findById(int id) throws DBException {
        try {
            Event event = eventDAO.findById(id);
            if (event == null) {
                throw new DBException(DBLiterals.eventNotExist);
            }
            return event;
        } catch (QuerySyntaxException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Возвращает пользователя по имени
     * или null при возникновении ошибки
     * @param name        Название мероприятия
     * @return            Найденное мероприятие
     */
    public Event findByName(String name) throws DBException {
        try {
            Event event = eventDAO.findByName(name);
            if (event == null) {
                throw new DBException(DBLiterals.eventNotExist);
            }
            return event;
        } catch (QuerySyntaxException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Создание мероприятия
     * @param user        Создатель
     * @param event       Новое мероприятие
     * @throws PersistenceException Ошибка сохранения
     */
    public void create(User user, Event event) throws DBException{
        try {
            eventDAO.create(user, event);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Обновление мероприятия
     * @param event       Мероприятие
     * @throws PersistenceException Ошибка сохранения
     */
    public void update(Event event) throws DBException{
        try {
            eventDAO.update(event);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Удаляет меропритятие
     * @param user        Создатель
     * @param event       Удаляемое мероприятие
     * @throws PersistenceException Ошибка сохранения
     */
    public void remove(User user, Event event) throws DBException {
        try {
            eventDAO.delete(user, event);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Возвразает мероприятие, соответствующее параметрам в query
     * @param query       EventQuery с параметрами поиска
     * @return            Список найденных мероприятий
     */
    public List<Event> find(EventQuery query) throws DBException {
        try {
            List<Event> events = eventDAO.find(query);
            if (events == null) {
                throw new DBException(DBLiterals.eventNotExist);
            }
            return events;
        } catch (SQLGrammarException | QueryParameterException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Возвращает все существующие мероприятия мероприятия
     * @return list of all events
     * @see Event
     */
    public List<Event> findAll() {
        try {
            List<Event> events = eventDAO.findAll();
            if (events == null) {
                throw new DBException(DBLiterals.eventNotExist);
            }
            return events;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Подписывает пользователя на участие в мероприятии
     * @param user        Пользователь
     * @param event       Мероприятие
     * @throws PersistenceException Ошибка сохранения
     */
    public void subscribe(User user, Event event) throws DBException{
        try {
            Set<Event> own = user.getCreatedEvents();
            Set<Event> subs = user.getSubscribes();
            if (own.contains(event) || subs.contains(event)) {
                return;
            }
            eventDAO.subscribe(user, event);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Отписывает пользователя от мероприятия
     * @param user        Пользователь
     * @param event       Мероприятие
     * @throws PersistenceException Ошибка сохранения
     */
    public void unsubscribe(User user, Event event) throws DBException{
        try {
            eventDAO.unsubscribe(user, event);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }
}