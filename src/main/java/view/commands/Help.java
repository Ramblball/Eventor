package view.commands;

import controller.EventController;
import controller.Keywords;
import database.model.Event;
import view.Message;

/**
 * Команда вызова помощи
 */
public class Help extends Command {
    EventController eventController = new EventController();
    @Override
    public String execute(Message message) {
        return eventController.getHelp();
    }
}
