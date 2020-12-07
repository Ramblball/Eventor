package view.commands;

import view.Message;

/**
 * Общий класс описывающий логику команд
 */
public abstract class Command implements ICommand {
    public abstract String execute(Message message);
}
