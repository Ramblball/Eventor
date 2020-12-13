package view.commands;

import view.TelegramMessage;

/**
 * Команда ошибки при вызове несуществующей команды
 */
public class Unknown extends Command {
    @Override
    public String execute(TelegramMessage telegramMessage) {
        return "Unknown command. Try to type \"help\"";
    }
}
