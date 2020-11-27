package view.commands;

import view.Message;

public class Unknown extends Command {
    @Override
    public String execute(Message message) {
        return "Unknown command. Try to type \"help\"";
    }
}
