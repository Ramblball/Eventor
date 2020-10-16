package database.services;

import database.dao.EventDAOImpl;
import database.model.Event;

import java.util.List;

public class EventService {
    EventDAOImpl EventDAO = new EventDAOImpl();

    public EventService() {}

    public Event findEvent(int id) {
        return EventDAO.findById(id);
    }

    public void saveEvent(Event event) {
        EventDAO.save(event);
    }

    public void deleteEvent(Event event) {
        EventDAO.delete(event);
    }

    public void updateEvent(Event event) {
        EventDAO.update(event);
    }

    public List<Event> findAll() {
        return EventDAO.findAll();
    }
}
