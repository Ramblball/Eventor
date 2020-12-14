package view.commands;

import view.TelegramMessage;

/**
 * Команда создания мероприятия
 */
public class CreateEventCommand extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return eventController
                .create(telegramMessage.getUser().getId(),
                        telegramMessage.getEventName(),
                        telegramMessage.getEventTime(),
                        telegramMessage.getEventPlace(),
                        telegramMessage.getEventDescription());
    }
}
