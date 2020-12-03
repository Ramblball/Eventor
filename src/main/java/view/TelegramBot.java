package view;

import controller.Keywords;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import view.commands.Help;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс для хранения в виде <Операция, Кол-во переданных аргументов>
 */
class Progress{
    String operation;
    int count;

    Progress(){
        operation = "";
        count = 0;
    }

    Progress(String operation, int count){
        this.operation = operation;
        this.count = count;
    }
}

/**
 * Класс инициализации и взаимодействия с телеграм ботом
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final static String botName = "eventor_oop_bot";
    private final DialogTransmitter dialogTransmitter = new DialogTransmitter();
    public static HashMap<User, Progress> userProgress = new HashMap<>();


    /**
     * Возрвращает имя бота
     *
     * @return Имя телеграм бота
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Возвращает токен бота из текстового файла
     *
     * @return Токен телеграм бота для доступа
     */
    @Override
    public String getBotToken() {
        String token = "";
        try {
            var br = new BufferedReader(new FileReader("token.txt"));
            token = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * Получает сообщение от пользователя, отдаёт на обработку Provider`у и отправляет ответ
     *
     * @param update Сообщение от пользователя в виде Update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String address = "@" +
                    botName +
                    " ";
            SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId())
                    .setText(dialogTransmitter.getMessage(update.getMessage().getFrom(), update.getMessage().getText()));
            message.setReplyMarkup(dialogTransmitter.getReplyKeyboardMarkup());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
