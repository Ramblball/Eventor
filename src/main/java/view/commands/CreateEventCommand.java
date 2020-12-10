package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда создания мероприятия
 */
public class CreateEventCommand extends Command {

    @Override
    public String execute(Message message) {
        return eventController.create(message.getUser().getId(), message.getEventName(), message.getEventTime(), message.getEventPlace(), message.getEventDescription());
    }
}
