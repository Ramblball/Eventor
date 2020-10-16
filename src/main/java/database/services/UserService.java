package database.services;

import database.dao.UserDAOImpl;
import database.model.User;

import java.util.List;

public class UserService {

    UserDAOImpl userDAO = new UserDAOImpl();

    public UserService() {}

    public User findUser(int id) {
        return userDAO.findById(id);
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

    public List<User> findAll() {
        return userDAO.findAll();
    }
}
