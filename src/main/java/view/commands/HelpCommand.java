package view.commands;

import view.TelegramMessage;

/**
 * Команда вызова помощи
 */
public class HelpCommand extends Command {
    @Override
    public String execute(TelegramMessage telegramMessage) {
        return eventController.getHelp();
    }
}
