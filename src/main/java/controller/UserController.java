package controller;

import controller.exception.NotAuthorizedException;
import database.DBException;
import database.model.*;

/**
 * Обеспечение взаимодействия пользователя с его моделью
 */
public class UserController extends Controller{

    /**
     * Создает нового пользователя
     * @param tgUser      Объект пользователя в телеграмме
     * @return            Результат создания пользователя
     */
    public String create(org.telegram.telegrambots.meta.api.objects.User tgUser) {
        try {
            User user = new User();
            user.setId(tgUser.getId());
            user.setName(tgUser.getFirstName());
            user.setUsername(tgUser.getUserName());
            userService.save(user);
            return String.format(Keywords.userCreated, tgUser.getFirstName());
        } catch (DBException e) {
            return e.getMessage();
        }
    }

    /**
     * Обновляет данные пользователя
     * @param tgUser      Объект пользователя в телеграмме
     * @return            Результат обновления
     */
    public String update(org.telegram.telegrambots.meta.api.objects.User tgUser) {
        try {
            User currentUser = getCurrent(tgUser.getId());
            userService.update(currentUser);
            currentUser.setName(tgUser.getFirstName());
            currentUser.setUsername(tgUser.getUserName());
            return String.format(Keywords.userUpdated, tgUser.getFirstName());
        } catch (NotAuthorizedException e) {
            return e.getMessage();
        } catch (DBException e) {
            return e.getMessage();
        }
    }
}