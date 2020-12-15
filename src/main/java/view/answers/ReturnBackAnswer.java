package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.TelegramBot;
import view.UserStateCache;

/**
 * Класс для диалога отмены действия
 */
public class ReturnBackAnswer extends Answer {

    @Override
    public String send(Message message) {
        telegramMessage = checkProgress(message);
        telegramKeyboard.createMainMenu();
        UserStateCache.setProgress(message.getFrom(), null);
        TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
        return "Выберите пункт меню";
    }
}
