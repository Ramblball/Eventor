package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда удаления мероприятия
 */
public class RemoveEventCommand extends Command {

    @Override
    public String execute(Message message) {
        return eventController.remove(message.getUser().getId(), message.getEventId());
    }
}
