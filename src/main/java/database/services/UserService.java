package database.services;

import database.dao.UserDAOImpl;
import database.model.Event;
import database.model.User;

public class UserService {

    UserDAOImpl userDAO = new UserDAOImpl();

    /**
     * Return User by provided ID
     *
     * @param id the id of the user
     * @return founded event
     * @see User
     */
    public User findUserById(int id) {
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
    public User findUserByName(String name) {
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
    public boolean saveUser(User user) {
        try {
            userDAO.save(user);
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
    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    /**
     * Update user in database
     *
     * @param user User obj for update
     * @see User
     */
    public void updateUser(User user) {
        userDAO.update(user);
    }

    /**
     * Save event in database
     * <p>
     * Add it to users event list
     *
     * @param user  creator
     * @param event new event
     * @return result success or not
     * @see User
     * @see Event
     */
    public boolean createEvent(User user, Event event) {
        try {
            userDAO.createEvent(user, event);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Remove event from users event list and from database
     * <p>
     * Unsubscribe from event users that are subscribed on it
     *
     * @param user  creator
     * @param event event for remove
     * @see User
     * @see Event
     */
    public void removeEvent(User user, Event event) {
        userDAO.removeEvent(user, event);
    }

    /**
     * Subscribe user to event
     *
     * @param user  subscriber
     * @param event event
     * @return result success or not
     */
    public boolean subscribe(User user, Event event) {
        try {
            userDAO.subscribe(user, event);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Unsubscribe user from event
     *
     * @param user  subscriber
     * @param event event
     */
    public void unsubscribe(User user, Event event) {
        userDAO.unsubscribe(user, event);
    }
}
