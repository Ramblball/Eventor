package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда отписки от мероприятия
 */
public class Unsubscribing extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.signOut(message.getUser().getUserName(), message.getEventId());
    }
}
