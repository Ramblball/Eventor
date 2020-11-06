package database.services;

import database.dao.EventDAOImpl;
import database.model.Event;

import java.util.List;

public class EventService {
    EventDAOImpl EventDAO = new EventDAOImpl();

    /**
     * Return Event by provided ID
     *
     * @param id  the id of the event
     * @return    founded event
     * @see       Event
     */
    public Event findEventById(int id) {
        return EventDAO.findById(id);
    }

    /**
     * Return Event by provided Name
     *
     * @param name  the name of the event
     * @return      founded event
     * @see         Event
     */
    public Event findEventByName(String name) {
        return EventDAO.findByName(name);
    }

    /**
     * return all Events
     *
     * @return      list of all events
     * @see         Event
     */
    public List<Event> findAll() {
        return EventDAO.findAll();
    }
}
