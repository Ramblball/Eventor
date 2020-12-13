package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.TelegramBot;
import view.TelegramKeyboard;

public class OperationMenuAnswer extends Answer{
    @Override
    public String send(Message message) {
        telegramMessage = checkProgress(message);
        telegramKeyboard.createOperationMenu();
        TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
        return "Что вы хотите сделать?";
    }
}
