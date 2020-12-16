package controller;

import controller.exception.NotAuthorizedException;
import database.exception.DBException;
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
     * Возвращает информацию о пользователе, работающим с ботом
     * @param id                        Уникальный идентификатор пользователя
     * @return                          Объект текущего пользователя
     * @throws DBException              Ошибка при обращении к бд
     * @throws NotAuthorizedException   Пользователь не найден
     */
    public User getCurrentUser(Integer id) throws DBException, NotAuthorizedException {
        User user = userService.findById(id);
        if (user == null) {
            throw new NotAuthorizedException(Keywords.USER_NOT_FOUND_EXCEPTION);
        }
        return user;
    }
}