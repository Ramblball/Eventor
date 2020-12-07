package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда подписки на мероприятие
 */
public class Subscribing extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.signIn(message.getUser().getId(), message.getEventId());
    }
}
