package view.commands;

import view.Message;

/**
 * Команда ошибки при вызове несуществующей команды
 */
public class Unknown extends Command {
    @Override
    public String execute(Message message) {
        return "Unknown command. Try to type \"help\"";
    }
}
