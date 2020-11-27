package view.commands;

import controller.UserController;
import view.Message;

public class LoggingIn extends Command{
    UserController userController = new UserController();

    @Override
    public String execute(Message message) {
        return userController.logIn(message.getUserName(), message.getUserPassword());
    }
}
