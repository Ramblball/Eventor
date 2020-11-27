package controller;

import database.model.*;

/**
 * Обеспечение взаимодействия пользователя с его моделью
 */
public class UserController extends Controller{

    /**
     * Создает нового пользователя
     * @param name        Имя пользователя
     * @return            Результат создания пользователя
     */
    public String create(String name) {
        if (name.length() > 32) {
            return Keywords.longName;
        }
        User user = new User();
        user.setName(name);
        boolean result = userService.save(user);
        if (!result) {
            return Keywords.exception;
        }
        return String.format(Keywords.userCreated, name);
    }

    /**
     * Обновляет данные пользователя
     * @param name        Новое имя пользователя
     * @return            Результат обновления
     */
    public String update(String name, String password) {
        if (name.length() > 32) {
            return Keywords.longName;
        }
        User currentUser = getCurrent(name);
        boolean result = userService.update(currentUser);
        if (!result) {
            return Keywords.exception;
        }
        return String.format(Keywords.userUpdated, name);
    }
}
