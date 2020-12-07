package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда отписки от мероприятия
 */
public class UnsubscribeCommand extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.signOut(message.getUser().getId(), message.getEventId());
    }
}
