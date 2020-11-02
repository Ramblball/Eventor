package controller;

import database.model.Category;
import database.model.Event;
import database.model.User;
import database.services.EventService;
import database.services.UserService;

import java.io.IOException;
import java.time.LocalDateTime;

public class Controller {
    private User current;
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();

    //TODO: Исключения, некорректный ввод
    public String execute(String command) throws IOException {
        var words = parse(command);
        switch (words[0]) {
            case "help": {
                return "This is a bot for creating events.\nTo create a user, type: \"Create user *name* *password*\"\n" +
                        "To log in, type: \"Login *name*\"\nTo create an event, type: \"Create event *name* *description*\"\n" +
                        "To find an event/user, type: \"Find *eventName|userName*\"\n" +
                        "To sign up for an event, type: \"Signup *eventName*\"";
            }
            case "create": {
                var type = words[1];
                var name = words[2];
                if (type.equals("user")) {
                    var password = words[3];
                    var user = new User();
                    user.setName(name);
                    user.setPassword(password);
                    userService.saveUser(user);
                    return "user " + user.getName() + " added";
                } else if (type.equals("event")) {
                    var event = new Event(name, "", LocalDateTime.now(), Category.Прогулка, "");
                    userService.createEvent(current, event);
                    return "event " + event.getName() + " added";
                }
            }
            case "find": {
                var eventId = Integer.parseInt(words[1]);
                var event = eventService.findEventById(eventId);
                if (event == null)
                    return "Event not found";
                return "Found an event: " + event.getName() + " " + event.getPlace() +
                        " " + " " + event.getTime() + " " + event.getDescription();

            }
            case "signup": {
                var eventName = words[1];
                var event = eventService.findEventByName(eventName);
                userService.subscribe(current, event);
                return current.getName() + " signed up for " + eventName;
            }
            case "login": {
                var userName = words[1];
                var user = userService.findUserByName(userName);
                if (user == null)
                    return "No such user";
                current = user;
                return "Welcome, " + current.getName();
            }
            default: {
                return "Unknown command. Try to type \"help\"";
            }
        }
    }

    private String[] parse(String command) {
        return command.toLowerCase().split(" ");
    }
}
