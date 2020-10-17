package database.services;

import database.dao.UserDAOImpl;
import database.model.Event;
import database.model.User;

public class UserService {

    UserDAOImpl userDAO = new UserDAOImpl();

    public UserService() {
    }

    public User findUserById(int id) {
        return userDAO.findById(id);
    }

    public User findUserByName(String name) {
        return userDAO.findByName(name);
    }

    public void saveUser(User user) {
        userDAO.save(user);
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void createEvent(User user, Event event) {
        userDAO.createEvent(user, event);
    }
    public void removeEvent(User user, Event event) {
        userDAO.removeEvent(user, event);
    }

    public void subscribe(User user, Event event) {
        userDAO.subscribe(user, event);
    }
    public void unsubscribe(User user, Event event) {
        userDAO.unsubscribe(user, event);
    }
}
