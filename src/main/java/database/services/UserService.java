package database.services;

import database.DBException;
import database.DBLiterals;
import database.dao.UserDAOImpl;
import database.model.Event;
import database.model.User;
import org.hibernate.hql.internal.ast.QuerySyntaxException;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Слой сервис для взаимодействия основного приложения с базой данных пользователей
 */
public class UserService {
    private static final UserDAOImpl userDAO = new UserDAOImpl();

    /**
     * Возвращает пользователя по id
     * или null при возникновении ошибки
     * @param id          id пользователя
     * @return            Найденный пользователь
     */
    public User findById(int id) throws DBException{
        try {
            User user = userDAO.findById(id);
            if (user == null) {
                throw new DBException(DBLiterals.userNotExist);
            }
            return user;
        } catch (QuerySyntaxException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Возвращает пользователя по имени
     * или null при возникновении ошибки
     * @param name        Имя пользователя
     * @return            Найденный пользователь
     */
    public User findByName(String name) throws DBException{
        try {
            User user = userDAO.findByName(name);
            if (user == null) {
                throw new DBException(DBLiterals.userNotExist);
            }
            return user;
        } catch (QuerySyntaxException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Сохранняет пользователя в базу данных
     * @param user                  Объект пользователя
     * @throws PersistenceException Ошибка сохранения
     */
    public void save(User user) throws DBException {
        try {
            userDAO.create(user);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Обновляет пользователя в базе данных
     * @param user        Объект пользователя
     * @throws PersistenceException Ошибка сохранения
     */
    public void update(User user) throws DBException {
        try {
            userDAO.update(user);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }

    /**
     * Удаляет пользователя из базы данных
     * @param user        Объект пользователя
     * @throws PersistenceException Ошибка сохранения
     */
    public void remove(User user) throws DBException {
        try {
            userDAO.remove(user);
        } catch (PersistenceException e) {
            throw new DBException(DBLiterals.dbExc, e);
        }
    }
}