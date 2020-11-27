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
            return session.createQuery(DBLiterals.findByNameQuery, User.class)
                    .setParameter(DBLiterals.name, name)
                    .getSingleResult();
        }
    }

    public void create(User user) {
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

    public void remove(User user) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }
}
