package controller;

import database.model.Category;
import database.model.Event;
import database.services.EventService;
import database.utils.EventQuery;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.StringJoiner;

public class EventController extends Controller {
    private final EventService eventService = new EventService();
    private StringBuilder stringBuilder = new StringBuilder();

    /**
     * @return manual
     */
    public String getHelp() {
        return Keywords.help;
    }

    /**
     * Validate events params before save
     *
     * @param name        Name of event
     * @param place       Place of event
     * @param description Description of event
     * @return Result of validation
     */
    public String checkEventsParams(String name, String time, String place, String description) {
        if (isLogout()) {
            return Keywords.unLogin;
        }
        if (name.length() > 32) {
            return Keywords.longName;
        }
        if (place.length() > 128) {
            return Keywords.longPlace;
        }
        if (description.length() > 512) {
            return Keywords.longDesc;
        }
        try {
            LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.dateTimeFormat));
        } catch (DateTimeParseException e) {
            return Keywords.invalidTime;
        }
        return null;
    }

    /**
     * Create new event
     *
     * @param name        of new event
     * @param time        of new event
     * @param place       of new event
     * @param description detailed information of new event
     * @return Result of creating
     */
    public String create(String name, String time, String place, String description) {
        String checking = checkEventsParams(name, time, place, description);
        if (checking != null) {
            return checking;
        }
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.dateTimeFormat));
        Event event = new Event(name, place, dateTime, Category.Прогулка, description);
        boolean result = eventService.create(currentUser, event);
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
     * Update an existing event
     *
     * @return result of update
     */
    public String update(String id, String name, String time, String place, String description) {
        String checking = checkEventsParams(name, time, place, description);
        if (checking != null) {
            return checking;
        }
        Event event;
        try {
            event = eventService.findById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return Keywords.idNotNumb;
        }
        if (event == null) {
            return Keywords.noEventId;
        }
        if (event.getUserId() != currentUser.getId()) {
            return Keywords.notOwnUpdate;
        }
        event.setName(name);
        event.setPlace(place);
        event.setDescription(description);
        boolean result = eventService.update(event);
        if (!result) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.event);
        stringBuilder.append(event.getName());
        stringBuilder.append(Keywords.updated);
        return stringBuilder.toString();
    }

    /**
     * Remove event
     *
     * @param id Id of event
     * @return Result of removing
     */
    public String remove(String id) {
        if (isLogout()) {
            return Keywords.unLogin;
        }
        Event event;
        try {
            event = eventService.findById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return Keywords.idNotNumb;
        }
        if (event.getUserId() != currentUser.getId()) {
            return Keywords.notOwnUpdate;
        }
        boolean result = eventService.remove(currentUser, event);
        if (!result) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.event);
        stringBuilder.append(event.getName());
        stringBuilder.append(Keywords.removed);
        return stringBuilder.toString();
    }

    /**
     * Return events found by query
     *
     * @param query Query with setted params
     * @return List of events found
     */
    public String find(EventQuery query) {
        if (isLogout()) {
            return Keywords.unLogin;
        }
        List<Event> events = eventService.find(query);
        if (events == null) {
            return Keywords.exception;
        }
        StringJoiner joiner = new StringJoiner("\n\n");
        for (Event event : events) {
            joiner.add(event.toString());
        }
        return joiner.toString();
    }

    /**
     * Return event by provided name
     *
     * @param name of sought event
     * @return success of finding event
     */
    public String findByName(String name) {
        var event = eventService.findByName(name);
        if (event == null) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.found);
        stringBuilder.append(Keywords.event);
        stringBuilder.append(event.toString());
        return stringBuilder.toString();
    }
}
