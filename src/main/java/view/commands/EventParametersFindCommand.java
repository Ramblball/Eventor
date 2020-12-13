package view.commands;

import database.utils.EventQuery;
import view.TelegramMessage;

/**
 * Команда создания мероприятия с критериями
 */
public class EventParametersFindCommand extends Command {
    @Override
    public String execute(TelegramMessage telegramMessage) {
        var eventQuery = new EventQuery();
        eventQuery.setName(telegramMessage.getEventName());
        eventQuery.setDescription(telegramMessage.getEventDescription());
        eventQuery.setTime(telegramMessage.getEventTime());
        eventQuery.setPlace(telegramMessage.getEventPlace());
        return eventController.findWithFilter(eventQuery);
    }
}
