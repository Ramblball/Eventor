package controller;

import database.model.User;
import database.services.EventService;
import database.services.UserService;

public class UserController {
    private static User current;
    private final EventService eventService = new EventService();
    private final UserService userService = new UserService();
    private StringBuilder stringBuilder = new StringBuilder();

    public String create(String name, String password) {
        var user = new User();
        user.setName(name);
        user.setPassword(password);
        userService.saveUser(user);
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.user);
        stringBuilder.append(user.getName());
        stringBuilder.append(Keywords.added);
        return stringBuilder.toString();
    }

    public String signUp(String name) {
        var event = eventService.findEventByName(name);
        userService.subscribe(current, event);
        stringBuilder = new StringBuilder();
        stringBuilder.append(current.getName());
        stringBuilder.append(Keywords.signed);
        stringBuilder.append(name);
        return stringBuilder.toString();
    }

    public String logIn(String name, String password) {
        var user = userService.findUserByName(name);
        if (user == null)
            return Keywords.wrongUser;
        if (!user.checkPassword(password))
            return Keywords.wrongPass;
        current = user;
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.welcome);
        stringBuilder.append(current.getName());
        return stringBuilder.toString();
    }

    public static User getCurrent(){
        return current;
    }
}
