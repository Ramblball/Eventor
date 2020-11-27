package database.services;

import database.dao.UserDAOImpl;
import database.model.Event;
import database.model.User;

import java.util.List;

/**
 * Слой сервис для взаимодействия основного приложения с базой данных пользователей
 */
public class UserService {
    UserDAOImpl userDAO = new UserDAOImpl();

    /**
     * Возвращает пользователя по id
     * или null при возникновении ошибки
     * @param id          id пользователя
     * @return            Найденный пользователь
     */
    public User findById(int id) {
        try {
            return userDAO.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Возвращает пользователя по имени
     * или null при возникновении ошибки
     * @param name        Имя пользователя
     * @return            Найденный пользователь
     */
    public User findByName(String name) {
        try {
            return userDAO.findByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Сохранняет пользователя в базу данных
     * @param user        Объект пользователя
     * @return            Результат выполнения
     */
    public boolean save(User user) {
        try {
            userDAO.create(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Обновляет пользователя в базе данных
     * @param user        Объект пользователя
     * @return            Результат обновления
     */
    public boolean update(User user) {
        try {
            userDAO.update(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Удаляет пользователя из базы данных
     * @param user        Объект пользователя
     * @return            Результат удаления
     */
    public boolean remove(User user) {
        try {
            userDAO.remove(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}