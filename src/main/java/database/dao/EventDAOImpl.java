package database.dao;

import database.model.Event;
import database.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EventDAOImpl {
    public Event findById(int id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Event.class, id);
        }
    }

    public void save(Event Event) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(Event);
            transaction.commit();
        }
    }

    public void update(Event Event) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(Event);
            transaction.commit();
        }
    }

    public void delete(Event Event) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(Event);
            transaction.commit();
        }
    }

    public List<Event> findAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return (List<Event>) session.createQuery("FROM Event").list();
        }
    }
}
