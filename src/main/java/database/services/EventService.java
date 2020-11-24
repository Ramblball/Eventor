package database.services;

import database.dao.EventDAOImpl;
import database.model.Event;
import database.utils.EventQuery;

import java.util.List;

public class EventService {
    EventDAOImpl eventDAO = new EventDAOImpl();

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
}
