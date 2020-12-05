package database.services;

import database.exception.DBException;
import database.DBLiterals;
import database.dao.UserDAOImpl;
import database.model.User;
import org.hibernate.hql.internal.ast.QuerySyntaxException;

import javax.persistence.PersistenceException;

/**
 * Класс сервис для взаимодействия основного приложения с базой данных пользователей
 */
public class UserService {
    private static final UserDAOImpl userDAO = new UserDAOImpl();

    /**
     * Метод для получения пользователя по уникальному идентификатору
     * @param id                    Уникальный идетификатор пользователя
     * @return                      Найденный пользователь
     * @throws DBException          Пользователь не найден
     * @throws QuerySyntaxException Ошибка синтаксиса запроса
     */
    public User findById(Integer id) throws DBException{
        try {
            User user = userDAO.findById(id);
            if (user == null) {
                throw new DBException(DBLiterals.USER_NOT_EXIST);
            }
            return user;
        } catch (QuerySyntaxException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод для получения пользователя по имени
     * @param name                  Имя пользователя
     * @return                      Найденный пользователь
     * @throws DBException          Пользователь не найден
     * @throws QuerySyntaxException Ошибка синтаксиса запроса
     */
    public User findByName(String name) throws DBException{
        try {
            User user = userDAO.findByName(name);
            if (user == null) {
                throw new DBException(DBLiterals.USER_NOT_EXIST);
            }
            return user;
        } catch (QuerySyntaxException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод для сохранения пользователя в базу данных
     * @param user                  Объект пользователя
     * @throws PersistenceException Ошибка сохранения
     */
    public void save(User user) throws DBException {
        try {
            userDAO.create(user);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.USER_ALREADY_EXIST_EXCEPTION, e);
        }
    }

    /**
     * Метод для обновления пользователя в базе данных
     * @param user                  Объект пользователя
     * @throws PersistenceException Ошибка сохранения
     */
    public void update(User user) throws DBException {
        try {
            userDAO.update(user);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }

    /**
     * Метод для удаления пользователя из базы данных
     * @param user                  Объект пользователя
     * @throws PersistenceException Ошибка сохранения
     */
    public void remove(User user) throws DBException {
        try {
            userDAO.remove(user);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.DB_EXCEPTION, e);
        }
    }
}