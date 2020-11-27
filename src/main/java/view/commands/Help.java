package view.commands;

import controller.EventController;
import controller.Keywords;
import database.model.Event;
import view.Message;

public class Help extends Command {
    EventController eventController = new EventController();
    @Override
    public String execute(Message message) {
        return eventController.getHelp();
    }
}
