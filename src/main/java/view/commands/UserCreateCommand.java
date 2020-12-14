package view.commands;

import view.TelegramMessage;

/**
 * Команда сохранения информаии о пользователе
 */
public class UserCreateCommand extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return userController.create(telegramMessage.getUser());
    }
}
