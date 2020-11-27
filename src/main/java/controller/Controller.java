package controller;

import database.model.User;
import database.services.EventService;
import database.services.UserService;

/**
 * 
 */
public abstract class Controller {
    protected static final UserService userService = new UserService();
    protected static final EventService eventService = new EventService();
    

    /**
     * Возвращает информацию о пользователе, работающем с ботом
     * @param name        Имя пользователя
     * @return            Объект текущего пользователя
     */
    public User getCurrent(String name) {
        User user = userService.findByName(name);
        if (user == null) {
            user = new User();
            user.setName(name);
            userService.save(user);
        }
        return user;
    }
}