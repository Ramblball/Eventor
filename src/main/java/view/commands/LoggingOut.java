package view.commands;

import controller.UserController;
import view.Message;

public class LoggingOut extends Command {
    UserController userController = new UserController();

    @Override
    public String execute(Message message) {
        return userController.logOut();
    }
}
