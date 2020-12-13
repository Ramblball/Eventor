package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.TelegramBot;
import view.TelegramKeyboard;
import view.TelegramMessage;

public class MainMenuAnswer extends Answer {

    @Override
    public String send(Message message) {
        telegramMessage = super.checkProgress(message);
        telegramKeyboard.createMainMenu();
        TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
        return commandMap.getCommandMap().get(message.getText()).execute(telegramMessage);
    }
}