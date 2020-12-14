package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.TelegramBot;

public class FindWeekAnswer extends Answer{
    @Override
    public String send(Message message) {
        telegramMessage = checkProgress(message);
        var received = message.getText();
        telegramKeyboard.createFindMenu();
        TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
        return commandMap.getCommandMap().get(received).execute(telegramMessage);
    }
}
