package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import view.Progress;
import view.TelegramBot;
import view.UserStateCache;

/**
 * Класс для запроса на ввод названия мероприятия
 */
public class OperationFindAnswer extends Answer {

    @Override
    public String send(Message message) {
        telegramMessage = checkProgress(message);
        String received = message.getText();
        User user = message.getFrom();
        telegramKeyboard.hideMenu();
        telegramMessage.setOperation(received);
        UserStateCache.setProgress(user, new Progress(telegramMessage, 0));
        TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
        return "Введите название мероприятия";
    }
}
