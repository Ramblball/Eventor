package view.commands;

import controller.EventController;
import view.Message;

public class Subscribing extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.signIn(message.getUserName(), message.getEventId());
    }
}
