package view.commands;

import controller.EventController;
import view.Message;

public class RemovingEvent extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.remove(message.getUserName(), message.getEventId());
    }
}
