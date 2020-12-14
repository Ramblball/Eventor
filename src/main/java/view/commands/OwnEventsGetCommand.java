package view.commands;

import view.TelegramMessage;

/**
 * Команда для получения мероприятий созданных пользователем
 */
public class OwnEventsGetCommand extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return eventController.getCreated(telegramMessage.getUser().getId());
    }
}
