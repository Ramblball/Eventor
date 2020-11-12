package controller;

import database.model.Category;
import database.model.Event;
import database.services.EventService;
import database.services.UserService;

import java.time.LocalDateTime;

public class EventController {
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();
    private StringBuilder stringBuilder = new StringBuilder();

    /**
     * @return manual
     */
    public String getHelp() {
        return Keywords.help;
    }

    /**
     * @param name        of new event
     * @param place       of new event
     * @param description detailed information of new event
     * @return success of adding new event for current user
     */
    public String create(String name, String place, String description) {
        if (name.length() > 32) {
            return Keywords.longName;
        }
        if (place.length() > 128) {
            return Keywords.longPlace;
        }
        if (description.length() > 256) {
            return Keywords.longDesc;
        }
        var event = new Event(name, place, LocalDateTime.now(), Category.Прогулка, description);
        boolean result = userService.createEvent(UserController.getCurrent(), event);
        if (!result) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.event);
        stringBuilder.append(event.getName());
        stringBuilder.append(Keywords.added);
        return stringBuilder.toString();
    }

    /**
     * @param name of sought event
     * @return success of finding event
     */
    public String findEvent(String name) {
        var event = eventService.findEventByName(name);
        if (event == null) {
            return Keywords.exception;
        }
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
}
