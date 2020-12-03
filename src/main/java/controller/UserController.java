package controller;

import controller.exception.NotAuthorizedException;
import database.DBException;
import database.model.*;
import org.apache.log4j.Logger;

/**
 * Обеспечение взаимодействия пользователя с его моделью
 */
public class UserController extends Controller{
    private static final Logger logger = Logger.getLogger(UserController.class);

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
            logger.error(e.getMessage(), e);
            return Keywords.userCreateException + e.getMessage();
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
            logger.error(e.getMessage(), e);
            return Keywords.authException;
        } catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.userUpdateException + e.getMessage();
        }
    }
}