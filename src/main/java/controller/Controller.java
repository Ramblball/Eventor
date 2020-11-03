package controller;

import database.model.Category;
import database.model.Event;
import database.model.User;
import database.services.EventService;
import database.services.UserService;

import java.time.LocalDateTime;

public class Controller {
    private User current;
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();

    public String getHelp() {
        return "This is a bot for creating events.\nTo create a user, type: \"Create user *name* *password*\"\n" +
                "To log in, type: \"Login *name*\"\nTo create an event, type: \"Create event *name* *description*\"\n" +
                "To find an event/user, type: \"Find *eventName|userName*\"\n" +
                "To sign up for an event, type: \"Signup *eventName*\"";
    }

    public String createUser(String name, String password) {
        var user = new User();
        user.setName(name);
        user.setPassword(password);
        userService.saveUser(user);
        return "user " + user.getName() + " added";
    }

    public String createEvent(String name, String place, String description) {
        var event = new Event(name, "", LocalDateTime.now(), Category.Прогулка, "");
        userService.createEvent(current, event);
        return "event " + event.getName() + " added";
    }

    public String findEvent(String name) {
        var event = eventService.findEventByName(name);
        return "Found an event: " + event.getName() + " " + event.getPlace() +
                " " + " " + event.getTime() + " " + event.getDescription();
    }

    public String signUp(String name) {
        var event = eventService.findEventByName(name);
        userService.subscribe(current, event);
        return current.getName() + " signed up for " + name;
    }

    public String logIn(String name) {
        var user = userService.findUserByName(name);
        if (user == null)
            return "No such user";
        current = user;
        return "Welcome, " + current.getName();
    }
}
