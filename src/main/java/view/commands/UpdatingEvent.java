package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда обновления мероприятия
 */
public class UpdatingEvent extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.update(message.getUser().getId(), message.getEventName(),
                message.getEventTime(), message.getEventPlace(), message.getEventDescription());
    }
}
