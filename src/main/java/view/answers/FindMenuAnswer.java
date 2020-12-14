package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.TelegramBot;

/**
 * Класс, отвечающий на поиск
 */
public class FindMenuAnswer extends Answer{

    @Override
    public String send(Message message) {
        telegramMessage = checkProgress(message);
        telegramKeyboard.createFindMenu();
        TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
        return "Как вы хотите искать?";
    }
}
