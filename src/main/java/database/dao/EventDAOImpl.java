package database.dao;

import database.model.Event;
import org.hibernate.Session;

import java.util.List;

public class EventDAOImpl extends DAO{

    public Event findById(int id) {
        try (Session session = openSession()) {
            return session.get(Event.class, id);
        }
    }

    public Event findByName(String name) {
        try (Session session = openSession()) {
            return session.createQuery("FROM Event WHERE name=:name", Event.class).setParameter("name", name).getSingleResult();
        }
    }

    public List<Event> findAll() {
        try (Session session = openSession()) {
            return session.createQuery("FROM Event", Event.class).getResultList();
        }
    }
}
