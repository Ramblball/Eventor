package view.commands;

import view.TelegramMessage;

/**
 * Команда для получения мероприятий, на которые пользователь подписан
 */
public class SubscribesGetCommand extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return eventController.getSubscribes(telegramMessage.getUser().getId());
    }
}
