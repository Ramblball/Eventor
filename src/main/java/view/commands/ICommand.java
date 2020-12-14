package view.commands;

import view.TelegramMessage;

/**
 * Интерфейс, описывающий поведение комманд
 */
public interface ICommand {
    String execute(TelegramMessage telegramMessage);
}
