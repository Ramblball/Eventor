package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.TelegramBot;

/**
 * Класс, отвечащий на возврат
 */
public class ReturnBackAnswer extends Answer{
    @Override
    public String send(Message message) {
        telegramMessage = checkProgress(message);
        telegramKeyboard.createMainMenu();
        TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
        return "Выберите пункт меню";
    }
}
