package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда удаления мероприятия
 */
public class RemoveEventCommand extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.remove(message.getUser().getId(), message.getEventId());
    }
}
