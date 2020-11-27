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
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.user);
        stringBuilder.append(user.getName());
        stringBuilder.append(Keywords.added);
        return stringBuilder.toString();
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
        return Keywords.userUpdateResult;
    }

    /**
     * Метод для подписки или отписки от мероприятия
     * @param id          Id мероприятия
     * @param f           Подписаться - true
     *                    Отписаться - false
     * @return            Результат выполнения
     */
    public String subscribeManager(String name, String id, boolean f) {
        try {
            Event event;
            event = eventService.findById(Integer.parseInt(id));
            if (event == null) {
                return Keywords.eventNotFound;
            }
            User currentUser = getCurrent(name);
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
    public String signIn(String name, String id) {
        String result = subscribeManager(name, id, true);
        if (result == null) {
            stringBuilder = new StringBuilder();
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
    public String signOut(String name, String id) {
        String result = subscribeManager(name, id, false);
        if (result == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(Keywords.unsigned);
            stringBuilder.append(id);
            return stringBuilder.toString();
        }
        return result;
    }
}
