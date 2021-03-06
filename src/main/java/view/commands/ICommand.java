package view.commands;

import view.TelegramMessage;

/**
 * Интерфейс, описывающий поведение команд
 */
public interface ICommand {

    /**
     * Метод для выполнения команды пользователя
     * @param telegramMessage   Обработанное сообщение пользователя
     * @return                  Результат исполнения
     */
    String execute(TelegramMessage telegramMessage);
}
