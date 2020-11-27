package database.services;

import database.dao.EventDAOImpl;
import database.model.Event;
import database.model.User;
import database.utils.EventQuery;

import java.util.List;

public class EventService {
    EventDAOImpl eventDAO = new EventDAOImpl();

    /**
     * Save event in database
     * <p>
     * Add it to users event list
     *
     * @param user  creator
     * @param event new event
     * @return result success or not
     * @see User
     * @see Event
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
     * Update existing event
     *
     * @param event Event for update
     * @return Result of update
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
     * Remove event from users event list and from database
     * <p>
     * Unsubscribe from event users that are subscribed on it
     *
     * @param user  creator
     * @param event event for remove
     * @see User
     * @see Event
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
     * Return Event by provided ID
     *
     * @param id the id of the event
     * @return founded event
     * @see Event
     */
    public Event findById(int id) {
        try {
            return eventDAO.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return Event by provided Name
     *
     * @param name the name of the event
     * @return founded event
     * @see Event
     */
    public Event findByName(String name) {
        try {
            return eventDAO.findByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return Events by provided query params
     *
     * @param query Query with setted search params
     * @return List of founded events
     * @see EventQuery
     */
    public List<Event> find(EventQuery query) {
        try {
            return eventDAO.find(query);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * return all Events
     *
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
     * Subscribe user to event
     *
     * @param user  subscriber
     * @param event event
     * @return result success or not
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
     * Unsubscribe user from event
     *
     * @param user  subscriber
     * @param event event
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
