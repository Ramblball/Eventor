package controller;

import database.model.User;

/**
 * @deprecated
 */
public abstract class Controller {
    protected static User currentUser;
    /**
     * Проверка, что пользователь не авторизирован
     * @return            авторизирован - false
     *                    Неавторизирован - true
     */
    protected boolean isLogout() {
        return currentUser == null;
    }
}