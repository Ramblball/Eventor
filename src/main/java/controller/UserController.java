package controller;

import database.model.User;
import database.services.EventService;
import database.services.UserService;

public class UserController {
    private static User current;
    private final EventService eventService = new EventService();
    private final UserService userService = new UserService();
    private StringBuilder stringBuilder = new StringBuilder();

    /**
     * @param name     of new user
     * @param password of new user
     * @return success of adding user
     */
    public String create(String name, String password) {
        if (password.length() < 4) {
            return Keywords.shortPass;
        }
        if (name.length() > 64) {
            return Keywords.longName;
        }
        var user = new User();
        user.setName(name);
        user.setPassword(password);
        boolean result = userService.saveUser(user);
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
     * @param name of event user want to sign on
     * @return success of signing up for an event
     */
    public String signUp(String name) {
        if (current == null) {
            return Keywords.unLogin;
        }
        var event = eventService.findEventByName(name);
        if (event == null) {
            return Keywords.exception;
        }
        boolean result = userService.subscribe(current, event);
        if (!result) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(current.getName().trim());
        stringBuilder.append(Keywords.signed);
        stringBuilder.append(name);
        return stringBuilder.toString();
    }

    /**
     * @param name     of existing user
     * @param password of existing user
     * @return success of logging
     */
    public String logIn(String name, String password) {
        var user = userService.findUserByName(name);
        if (user == null)
            return Keywords.wrongUser;
        if (!user.checkPassword(password))
            return Keywords.wrongPass;
        current = user;
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.welcome);
        stringBuilder.append(current.getName().trim());
        return stringBuilder.toString();
    }

    /**
     * @return current User object for eventController
     */
    public static User getCurrent() {
        return current;
    }
}
