package database.dao;

import database.DBLiterals;
import database.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Класс запросов к базе данных к таблице пользователей
 */
public class UserDAOImpl extends DAO{

    /**
     * Метод для отправки запроса на поиск по уникальному идентификатору
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
     * Метод для отправки запроса на обновление пользователя
     * @param user          Объект пользователя
     */
    public void update(User user) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }
}