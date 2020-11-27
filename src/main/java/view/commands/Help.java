package view.commands;

import controller.Keywords;
import view.Message;

public class Help extends Command {
    @Override
    public String execute(Message message) {
        return Keywords.help;
    }
}
