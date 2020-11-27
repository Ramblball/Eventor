package view.commands;

import controller.EventController;
import view.Message;

public class Unsubscribing extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.signOut(message.getUserName(), message.getEventId());
    }
}
