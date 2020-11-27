package view.commands;

import controller.EventController;
import database.utils.EventQuery;
import view.Message;

/**
 * Команда создания мероприятия с критериями
 */
public class FindingEventParameters extends Command {
    EventController eventController = new EventController();
    @Override
    public String execute(Message message) {
        var eventQuery = new EventQuery();
        eventQuery.setName(message.getEventName());
        eventQuery.setCategory(message.getEventCategory());
        eventQuery.setDescription(message.getEventDescription());
        eventQuery.setTime(message.getEventTime());
        eventQuery.setPlace(message.getEventPlace());
        return eventController.find(eventQuery);
    }
}
