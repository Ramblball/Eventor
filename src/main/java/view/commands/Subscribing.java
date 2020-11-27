package view.commands;

import controller.UserController;
import view.Message;

public class Subscribing extends Command {
    UserController userController = new UserController();

    @Override
    public String execute(Message message) {
        return userController.signIn(message.getEventId());
    }
}
