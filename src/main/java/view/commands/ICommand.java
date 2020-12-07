package view.commands;

import view.Message;

public interface ICommand {
    String execute(Message message);
}
