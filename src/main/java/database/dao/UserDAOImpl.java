package database.dao;

import database.DBLiterals;
import database.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Класс слой запросов к базе данных к таблице пользователей
 */
public class UserDAOImpl extends DAO{

    /**
     * Метод отправляющий запрос на поиск по уникальному идентификатору
     * @param id            Уникальный идентификатор пользователя
     * @return              Объект пользователя
     */
    public User findById(int id) {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.USER_WITH_CREATED);
            session.enableFetchProfile(DBLiterals.USERS_WITH_SUBSCRIBES);
            return session.get(User.class, id);
        }
    }

    /**
     * Метод отправляющий запрос на поиск по имени
     * @param name          Имя пользователя
     * @return              Объект пользователя
     */
    public User findByName(String name) {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.USER_WITH_CREATED);
            session.enableFetchProfile(DBLiterals.USERS_WITH_SUBSCRIBES);
            return session.createQuery(DBLiterals.FIND_BY_NAME_QUERY, User.class)
                    .setParameter(DBLiterals.NAME, name)
                    .getSingleResult();
        }
    }

    /**
     * Метод отправляющий запрос на создание пользователя
     * @param user          Объект пользователя
     */
    public void create(User user) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    /**
     * Метод отправляющий запрос на обновление пользователя
     * @param user          Объект пользователя
     */
    public void update(User user) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }

    /**
     * Метод отправляющий запрос на удаление пользователя
     * @param user          Объект пользователя
     */
    public void remove(User user) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }
}