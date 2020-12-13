package view.commands;

import view.TelegramMessage;

/**
 * Команда обновления мероприятия
 */
public class UpdateEventCommand extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return eventController.update(telegramMessage.getUser().getId(), telegramMessage.getEventName(),
                telegramMessage.getEventTime(), telegramMessage.getEventPlace(), telegramMessage.getEventDescription());
    }
}
