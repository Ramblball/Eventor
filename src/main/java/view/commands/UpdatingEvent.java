package view.commands;

import controller.EventController;
import view.Message;

public class UpdatingEvent extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.update(message.getEventId(), message.getEventName(), message.getEventTime(),
                message.getEventPlace(), message.getEventDescription());
    }
}
