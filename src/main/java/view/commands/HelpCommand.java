package view.commands;

import controller.EventController;
import controller.Keywords;
import database.model.Event;
import view.Message;

/**
 * Команда вызова помощи
 */
public class HelpCommand extends Command {
    @Override
    public String execute(Message message) {
        return eventController.getHelp();
    }
}
