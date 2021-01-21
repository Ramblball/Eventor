package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.User;
import view.exception.NotAuthorizedException;
import database.exception.DBException;

/**
 * Класс для взаимодействия пользователя с его моделью
 */
public class UserController extends Controller{
    private static final Logger logger = LogManager.getLogger(UserController.class);

    /**
     * Метод для создания нового пользователя
     * @param tgUser      Объект пользователя в телеграмме
     * @return            Результат создания пользователя
     */
    public String create(User tgUser) {
        try {
            database.model.User user = new database.model.User();
            user.setId(tgUser.getId());
            user.setName(tgUser.getFirstName());
            user.setUsername(tgUser.getUserName());
            userService.save(user);
            return String.format(Keywords.USER_CREATED, tgUser.getFirstName());
        } catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.USER_CREATE_EXCEPTION + e.getMessage();
        }
    }

    /**
     * Метод для обновления данных пользователя
     * @param tgUser      Объект пользователя в телеграмме
     * @return            Результат обновления
     */
    public String update(User tgUser) {
        try {
            database.model.User currentUser = getCurrentUser(tgUser.getId());
            userService.update(currentUser);
            currentUser.setName(tgUser.getFirstName());
            currentUser.setUsername(tgUser.getUserName());
            return String.format(Keywords.USER_UPDATED, tgUser.getFirstName());
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION + e.getMessage();
        } catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.USER_UPDATE_EXCEPTION + e.getMessage();
        }
    }
}