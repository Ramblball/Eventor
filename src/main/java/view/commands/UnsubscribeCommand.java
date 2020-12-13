package view.commands;

import view.TelegramMessage;

/**
 * Команда отписки от мероприятия
 */
public class UnsubscribeCommand extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return eventController.signOut(telegramMessage.getUser().getId(), telegramMessage.getEventId());
    }
}
