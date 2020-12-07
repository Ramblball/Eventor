package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда поиска мероприятий по имени
 */
public class EventFindCommand extends Command {
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.findByName(message.getEventName());
    }
}
