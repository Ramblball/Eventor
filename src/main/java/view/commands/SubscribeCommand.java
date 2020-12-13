package view.commands;

import view.TelegramMessage;

/**
 * Команда подписки на мероприятие
 */
public class SubscribeCommand extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return eventController.signIn(telegramMessage.getUser().getId(), telegramMessage.getEventId());
    }
}
