package controller;

import database.model.Category;
import database.model.Event;
import database.model.User;
import database.services.EventService;
import database.services.UserService;
import javassist.compiler.ast.Keyword;

import java.security.Key;
import java.time.LocalDateTime;

public class Controller {
    private User current;
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();
    private StringBuilder stringBuilder = new StringBuilder();

    public String getHelp() {
        return Keywords.help;
    }

    public String createUser(String name, String password) {
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

    public String createEvent(String name, String place, String description) {
        var event = new Event(name, "", LocalDateTime.now(), Category.Прогулка, "");
        userService.createEvent(current, event);
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.event);
        stringBuilder.append(event.getName());
        stringBuilder.append(Keywords.added);
        return stringBuilder.toString();
    }

    public String findEvent(String name) {
        var event = eventService.findEventByName(name);
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.found);
        stringBuilder.append(Keywords.event);
        stringBuilder.append(event.getName());
        stringBuilder.append(' ');
        stringBuilder.append(event.getPlace());
        stringBuilder.append(' ');
        stringBuilder.append(event.getTime());
        stringBuilder.append(' ');
        stringBuilder.append(event.getDescription());
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
}
