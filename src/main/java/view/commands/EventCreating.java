package view.commands;

import controller.EventController;
import view.Message;

public class EventCreating extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.create(message.getEventName(), message.getEventTime(), message.getEventPlace(), message.getEventDescription());
    }
}
