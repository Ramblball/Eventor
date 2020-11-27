package view.commands;

import controller.UserController;
import view.Message;

public class Unsubscribing extends Command {
    UserController userController = new UserController();

    @Override
    public String execute(Message message) {
        return userController.signOut(message.getUserName(), message.getEventId());
    }
}
