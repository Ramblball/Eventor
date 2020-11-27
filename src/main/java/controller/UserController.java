package controller;

import database.model.*;
import database.services.*;

/**
 * Класс-слой зваимодействия между пользовательским представлением и внутрееней моделью приложения
 * Обеспечивает взаимодействие пользователя с его моделью
 */
public class UserController extends Controller{
    private final EventService eventService = new EventService();
    private final UserService userService = new UserService();
    private StringBuilder stringBuilder = new StringBuilder();
    /**
     * Создает нового пользователя
     * @deprecated
     * @param name        Имя пользователя
     * @return            Результат создания пользователя
     */
    public String create(String name) {
        String password = "aaaaa";
        if (password.length() < 4) {
            return Keywords.shortPass;
        }
        if (name.length() > 32) {
            return Keywords.longName;
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        boolean result = userService.save(user);
        if (!result) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.user);
        stringBuilder.append(user.getName());
        stringBuilder.append(Keywords.added);
        return stringBuilder.toString();
    }
    /**
     * Обновляет данные пользователя
     * @deprecated
     * @param name        Новое имя пользователя
     * @param password    Новый пароль
     * @return            Результат обновления
     */
    public String update(String name, String password) {
        if (isLogout()) {
            return Keywords.unLogin;
        }
        if (password.length() < 4) {
            return Keywords.shortPass;
        }
        if (name.length() > 32) {
            return Keywords.longName;
        }
        currentUser.setName(name);
        currentUser.setPassword(password);
        boolean result = userService.update(currentUser);
        if (!result) {
            return Keywords.exception;
        }
        return Keywords.userUpdateResult;
    }
    /**
     * Метод для подписки или отписки от мероприятия
     * @param id          Id мероприятия
     * @param f           Подписаться - true
     *                    Отписаться - false
     * @return            Результат выполнения
     */
    public String subscribeManager(String id, boolean f) {
        try {
            if (isLogout()) {
                return Keywords.unLogin;
            }
            Event event;
            event = eventService.findById(Integer.parseInt(id));
            if (event == null) {
                return Keywords.eventNotFound;
            }
            boolean result;
            if (f) {
                result = userService.subscribe(currentUser, event);
            } else {
                result = userService.unsubscribe(currentUser, event);
            }
            if (!result) {
                return Keywords.exception;
            }
        } catch (NumberFormatException e) {
            return Keywords.idNotNumb;
        }
        return null;
    }
    /**
     * Подписывает пользователя на мероприятие
     * @param id          Id мероприятия
     * @return            Результат подписки
     */
    public String signIn(String id) {
        String result = subscribeManager(id, true);
        if (result == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(currentUser.getName());
            stringBuilder.append(Keywords.signed);
            stringBuilder.append(id);
            return stringBuilder.toString();
        }
        return result;
    }
    /**
     * Отписывает пользователя от мероприятия
     * @param id          Id мероприятия
     * @return            Результат отписки
     */
    public String signOut(String id) {
        String result = subscribeManager(id, false);
        if (result == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(currentUser.getName());
            stringBuilder.append(Keywords.unsigned);
            stringBuilder.append(id);
            return stringBuilder.toString();
        }
        return result;
    }
    /**
     * Авторизирует пользователя в системе
     * @deprecated
     * @param name     Имя пользователя
     * @param password Пароль
     * @return         Результат авторизации
     */
    public String logIn(String name, String password) {
        User user = userService.findByName(name);
        if (user == null)
            return Keywords.wrongUser;
        if (!user.checkPassword(password))
            return Keywords.wrongPass;
        currentUser = user;
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.welcome);
        stringBuilder.append(currentUser.getName());
        return stringBuilder.toString();
    }
    /**
     * Выход пользователя из системы
     * @deprecated
     * @return            Результат выхода
     */
    public String logOut(){
        if(currentUser == null)
            return Keywords.unAuthorized;
        currentUser = null;
        return Keywords.loggedOut;
    }
}
