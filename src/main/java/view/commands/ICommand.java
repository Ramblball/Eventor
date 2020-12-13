package view.commands;

import view.TelegramMessage;

public interface ICommand {
    String execute(TelegramMessage telegramMessage);
}
