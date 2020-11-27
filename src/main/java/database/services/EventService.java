package database.services;

import database.dao.EventDAOImpl;
import database.model.Event;
import database.model.User;
import database.utils.EventQuery;

import java.util.List;

/**
 * Слой сервис для взаимодействия основного приложения с базой данных мероприятий
 */
public class EventService {
    EventDAOImpl eventDAO = new EventDAOImpl();

    /**
     * Возвращает мероприятие по id
     * или null при возникновении ошибки
     * @param id          Id мероприятия
     * @return            Найденное мероприятие
     */
    public Event findById(int id) {
        try {
            return eventDAO.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Возвращает пользователя по имени
     * или null при возникновении ошибки
     * @param name        Название мероприятия
     * @return            Найденное мероприятие
     */
    public Event findByName(String name) {
        try {
            return eventDAO.findByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Создание мероприятия
     * @param user        Создатель
     * @param event       Новое мероприятие
     * @return            Результат сохранения
     */
    public boolean create(User user, Event event) {
        try {
            eventDAO.create(user, event);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Обновление мероприятия
     * @param event       Мероприятие
     * @return            Результат обновления
     */
    public boolean update(Event event) {
        try {
            eventDAO.update(event);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Удаляет меропритятие
     * @param user        Создатель
     * @param event       Удаляемое мероприятие
     */
    public boolean remove(User user, Event event) {
        try {
            eventDAO.delete(user, event);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Возвразает мероприятие, соответствующее параметрам в query
     * @param query       EventQuery с параметрами поиска
     * @return            Список найденных мероприятий
     */
    public List<Event> find(EventQuery query) {
        try {
            return eventDAO.find(query);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Возвращает все существующие мероприятия мероприятия
     * @return list of all events
     * @see Event
     */
    public List<Event> findAll() {
        try {
            return eventDAO.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Подписывает пользователя на участие вв мероприятии
     * @param user        Пользователь
     * @param event       Мероприятие
     * @return            Рузельтат подписки
     */
    public boolean subscribe(User user, Event event) {
        try {
            List<Event> own = user.getCreatedEvents();
            List<Event> subs = user.getSubscribes();
            if (own.contains(event) || subs.contains(event)) {
                return false;
            }
            eventDAO.subscribe(user, event);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Отписывает пользователя от мероприятия
     * @param user        Пользователь
     * @param event       Мероприятие
     * @return            Рузельтат отписки
     */
    public boolean unsubscribe(User user, Event event) {
        try {
            eventDAO.unsubscribe(user, event);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}