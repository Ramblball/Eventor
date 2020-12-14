package view.commands;

import view.TelegramMessage;

/**
 * Команда удаления мероприятия
 */
public class RemoveEventCommand extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return eventController
                .remove(telegramMessage.getUser().getId(), telegramMessage.getEventName());
    }
}
