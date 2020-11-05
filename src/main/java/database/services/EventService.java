package database.services;

import database.dao.EventDAOImpl;
import database.model.Event;

import java.util.List;

public class EventService {
    EventDAOImpl EventDAO = new EventDAOImpl();

    public Event findEventById(int id) {
        return EventDAO.findById(id);
    }

    public Event findEventByName(String name) {
        return EventDAO.findByName(name);
    }

    public List<Event> findAll() {
        return EventDAO.findAll();
    }
}
