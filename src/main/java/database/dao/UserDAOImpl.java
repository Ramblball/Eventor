package database.dao;

import database.DBLiterals;
import database.model.Event;
import database.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAOImpl extends DAO{

    public User findById(int id) {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.usersWithSubscribes);
            return session.get(User.class, id);
        }
    }

    public User findByName(String name) {
        try (Session session = openSession()) {
            return session.createQuery("FROM User WHERE name=:name", User.class).setParameter(DBLiterals.name, name).getSingleResult();
        }
    }

    public void save(User user) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    public void update(User user) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }

    public void delete(User user) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }

    public void createEvent(User user, Event event) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.refresh(user);
            user.addCreatedEvent(event);
            session.save(event);
            transaction.commit();
        }
    }
    public void removeEvent(User user, Event event) {
        try (Session session = openSession()) {
            session.refresh(user);
            user.removeCreatedEvent(event);
        }
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(event);
            transaction.commit();
        }
    }

    public void subscribe(User user, Event event) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.refresh(user);
            session.refresh(event);
            user.addSubscribe(event);
            transaction.commit();
        }
    }
    public void unsubscribe(User user, Event event) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.refresh(user);
            session.refresh(event);
            user.removeSubscribe(event);
            transaction.commit();
        }
    }
}
