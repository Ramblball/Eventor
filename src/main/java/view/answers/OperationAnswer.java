package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.Progress;
import view.TelegramBot;
import view.UserStateCache;

public class OperationAnswer extends Answer{
    @Override
    public String send(Message message) {
        telegramMessage = super.checkProgress(message);
        var received = message.getText();
        var user = message.getFrom();
        telegramKeyboard.hideMenu();
        telegramMessage.setOperation(received);
        UserStateCache.setProgress(user, new Progress(telegramMessage, 0));
        TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
        return "Введите название мероприятия";
    }
}
