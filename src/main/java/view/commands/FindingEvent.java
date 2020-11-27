package view.commands;

import controller.EventController;
import view.Message;

public class FindingEvent extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.findByName(message.getEventName());
    }
}
