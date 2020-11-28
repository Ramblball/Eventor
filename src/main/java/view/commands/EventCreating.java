package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда создания мероприятия
 */
public class EventCreating extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.create(message.getUserName(), message.getEventName(), message.getEventTime(), message.getEventPlace(), message.getEventDescription());
    }
}