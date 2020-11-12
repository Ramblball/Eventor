package database.services;

import database.dao.EventDAOImpl;
import database.model.Event;

import java.util.List;

public class EventService {
    EventDAOImpl EventDAO = new EventDAOImpl();

    /**
     * Return Event by provided ID
     *
     * @param id the id of the event
     * @return founded event
     * @see Event
     */
    public Event findEventById(int id) {
        try {
            return EventDAO.findById(id);
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
    public Event findEventByName(String name) {
        try {
            return EventDAO.findByName(name);
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
            return EventDAO.findAll();
        } catch (Exception e) {
            return null;
        }
    }
}
