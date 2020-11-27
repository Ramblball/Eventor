package controller;

import database.model.User;
import database.services.UserService;

public abstract class Controller {
    private static final UserService service = new UserService();

    public User getCurrent(String name) {
        User user = service.findByName(name);
        if (user == null) {
            user = new User();
            user.setName(name);
            service.save(user);
        }
        return user;
    }
}