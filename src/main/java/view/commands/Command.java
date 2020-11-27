package view.commands;

import view.Message;

/**
 * Общий класс описывающий логику команд
 */
public abstract class Command {
    public abstract String execute(Message message);
}
