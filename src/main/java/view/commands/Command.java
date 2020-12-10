package view.commands;

import controller.EventController;
import controller.UserController;
import view.Message;

/**
 * Общий класс описывающий логику команд
 */
public abstract class Command implements ICommand {
    protected EventController eventController = new EventController();
    protected UserController userController = new UserController();

    public abstract String execute(Message message);
}
