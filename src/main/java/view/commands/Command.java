package view.commands;

import controller.EventController;
import controller.UserController;
import view.TelegramMessage;

/**
 * Класс, описывающий общую логику команд
 */
public abstract class Command implements ICommand {
    protected EventController eventController = new EventController();
    protected UserController userController = new UserController();

    public abstract String execute(TelegramMessage telegramMessage);
}
