package view.commands;

import view.Message;

/**
 * Команда ошибки при неверно введенных аргументах
 */
public class FewArguments extends Command {
    @Override
    public String execute(Message message) {
        return "Too few arguments. Try to type \"help\"";
    }
}
