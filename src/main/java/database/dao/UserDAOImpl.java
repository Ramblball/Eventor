package database.dao;

import database.DBLiterals;
import database.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Класс слой запросов к базе пользователей
 */
public class UserDAOImpl extends DAO{

    /**
     * Запрос на поиск по id
     * @param id          Id пользователя
     * @return            Объект пользователя
     */
    public User findById(int id) {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.usersWithSubscribes);
            return session.get(User.class, id);
        }
    }

    /**
     * Запрос на поиск по имени
     * @param name        Имя пользователя
     * @return            Объект пользователя
     */
    public User findByName(String name) {
        try (Session session = openSession()) {
            return session.createQuery(DBLiterals.findByNameQuery, User.class)
                    .setParameter(DBLiterals.name, name)
                    .getSingleResult();
        }
    }

    /**
     * Запрос на создание пользователя
     * @param user        Объект пользователя
     */
    public void create(User user) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    /**
     * Запрос на обновление пользователя
     * @param user        Объект пользователя
     */
    public void update(User user) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }

    /**
     * Запрос на удаление пользователя
     * @param user        Объект пользователя
     */
    public void remove(User user) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }
}