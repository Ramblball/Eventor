package view.commands;

import controller.UserController;
import view.Message;

public class UserCreating extends Command {
    private final UserController userController = new UserController();
    @Override
    public String execute(Message message) {
        return userController.create(message.getUserName());
    }
}
