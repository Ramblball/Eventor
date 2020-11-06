package database.services;

import database.dao.UserDAOImpl;
import database.model.Event;
import database.model.User;

public class UserService {

    UserDAOImpl userDAO = new UserDAOImpl();

    /**
     * Return User by provided ID
     *
     * @param id  the id of the user
     * @return    founded event
     * @see       User
     */
    public User findUserById(int id) {
        return userDAO.findById(id);
    }

    /**
     * Return User by provided name
     *
     * @param name  the name of the user
     * @return      founded event
     * @see         User
     */
    public User findUserByName(String name) {
        return userDAO.findByName(name);
    }

    /**
     * Save user to database
     *
     * @param user  new user
     * @see         User
     */
    public void saveUser(User user) {
        userDAO.save(user);
    }

    /**
     * Remove user from database
     *
     * @param user  User obj for remove
     * @see         User
     */
    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    /**
     * Update user in database
     *
     * @param user  User obj for update
     * @see         User
     */
    public void updateUser(User user) {
        userDAO.update(user);
    }

    /**
     * Save event in database
     *
     * Add it to users event list
     *
     * @param user   creator
     * @param event  new event
     * @see          User
     * @see          Event
     */
    public void createEvent(User user, Event event) {
        userDAO.createEvent(user, event);
    }

    /**
     * Remove event from users event list and from database
     *
     * Unsubscribe from event users that are subscribed on it
     *
     * @param user   creator
     * @param event  event for remove
     * @see          User
     * @see          Event
     */
    public void removeEvent(User user, Event event) {
        userDAO.removeEvent(user, event);
    }

    /**
     * Subscribe user to event
     *
     * @param user   subscriber
     * @param event  event
     */
    public void subscribe(User user, Event event) {
        userDAO.subscribe(user, event);
    }

    /**
     * Unsubscribe user from event
     *
     * @param user   subscriber
     * @param event  event
     */
    public void unsubscribe(User user, Event event) {
        userDAO.unsubscribe(user, event);
    }
}
