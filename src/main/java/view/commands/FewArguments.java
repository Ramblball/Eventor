package view.commands;

import view.TelegramMessage;

/**
 * Команда ошибки при неверно введенных аргументах
 */
public class FewArguments extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return "Too few arguments. Try to type \"help\"";
    }
}
