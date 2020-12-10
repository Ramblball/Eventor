package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда обновления мероприятия
 */
public class UpdateEventCommand extends Command {

    @Override
    public String execute(Message message) {
        return eventController.update(message.getUser().getId(), message.getEventName(),
                message.getEventTime(), message.getEventPlace(), message.getEventDescription());
    }
}
