package controller;

import controller.exception.NotAuthorizedException;
import database.DBException;
import database.model.User;
import database.services.EventService;
import database.services.UserService;

/**
 * Общий класс слоев взаимодействия представления и модели приложения
 */
public abstract class Controller {
    protected static final Validator validator = new Validator();
    protected static final UserService userService = new UserService();
    protected static final EventService eventService = new EventService();

    /**
     * Возвращает информацию о пользователе, работающем с ботом
     * @param id        Id пользователя
     * @return            Объект текущего пользователя
     */
    public User getCurrent(Integer id) throws DBException, NotAuthorizedException {
        User user = userService.findById(id);
        if (user == null) {
            throw new NotAuthorizedException(Keywords.notAuth);
        }
        return user;
    }
}