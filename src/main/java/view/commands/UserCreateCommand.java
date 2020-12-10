package view.commands;

import controller.UserController;
import view.Message;

/**
 * Команда сохранения информаии о пользователе
 */
public class UserCreateCommand extends Command {
    @Override
    public String execute(Message message) {
        return userController.create(message.getUser());
    }
}
