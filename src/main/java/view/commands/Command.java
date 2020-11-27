package view.commands;

import view.Message;

public abstract class Command {
    public abstract String execute(Message message);
}
