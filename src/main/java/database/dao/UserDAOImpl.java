package database.dao;

import database.DBLiterals;
import database.model.User;
import database.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Класс запросов к базе данных к таблице пользователей
 */
public class UserDAOImpl implements DAO<User>{

    /**
     * Метод создания сессии работы с пользователем
     * @return            Сессия взаимодействия с базой данных
     */
    private Session openSession(){
        return HibernateSessionFactory.getSessionFactory().openSession();
    }

    /**
     * Метод для отправки запроса на поиск по уникальному идентификатору
     * @param id            Уникальный идентификатор пользователя
     * @return              Объект пользователя
     */
    @Override
    public User findById(int id) {
        try (Session session = openSession()) {
            session.enableFetchProfile(DBLiterals.USER_WITH_CREATED);
            session.enableFetchProfile(DBLiterals.USERS_WITH_SUBSCRIBES);
            session.enableFetchProfile(DBLiterals.EVENT_WITH_SUBSCRIBERS);
            return session.get(User.class, id);
        }
    }

    /**
     * Метод отправляющий запрос на создание пользователя
     * @param entity          Объект пользователя
     */
    @Override
    public void create(User entity) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        }
    }

    /**
     * Метод для отправки запроса на обновление пользователя
     * @param entity          Объект пользователя
     */
    @Override
    public void update(User entity) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        }
    }

    @Override
    public void remove(User entity) {
        try (Session session = openSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        }
    }
}