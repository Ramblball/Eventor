package database.services;

import database.dao.UserDAOImpl;
import database.model.Event;
import database.model.User;

import java.util.List;

public class UserService {

    UserDAOImpl userDAO = new UserDAOImpl();

    /**
     * Return User by provided ID
     *
     * @param id the id of the user
     * @return founded event
     * @see User
     */
    public User findById(int id) {
        try {
            return userDAO.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return User by provided name
     *
     * @param name the name of the user
     * @return founded event
     * @see User
     */
    public User findByName(String name) {
        try {
            return userDAO.findByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Save user to database
     *
     * @param user new user
     * @return result success or not
     * @see User
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
     * Remove user from database
     *
     * @param user User obj for remove
     * @see User
     */
    public boolean remove(User user) {
        try {
            userDAO.remove(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Update user in database
     *
     * @param user User obj for update
     * @see User
     */
    public boolean update(User user) {
        try {
            userDAO.update(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
