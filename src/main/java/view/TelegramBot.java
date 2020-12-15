package view;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Класс инициализации и взаимодействия с телеграм ботом
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final static String botName = "eventor_oop_bot";
    private final DialogTransmitter dialogTransmitter = new DialogTransmitter();
    public static ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();


    /**
     * Возрвращает имя бота
     * @return          Имя телеграм бота
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Возвращает токен бота из текстового файла
     * @return          Токен телеграм бота для доступа
     */
    @Override
    public String getBotToken() {
        String token;
        try {
            BufferedReader br = new BufferedReader(new FileReader("token.txt"));
            token = br.readLine();
        } catch (IOException e) {
            token = System.getenv("TOKEN");
        }
        return token;
    }

    /**
     * Получает сообщение от пользователя и отдаёт на обработку в DialogTransmitter
     * @param update    Сообщение от пользователя в виде Update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            new Thread(() -> {
                synchronized (update.getMessage().getFrom()) {
                    SendMessage message = new SendMessage()
                            .setChatId(update.getMessage().getChatId())
                            .setText(dialogTransmitter.getMessage(update.getMessage()))
                            .setParseMode("HTML");
                    message.setReplyMarkup(replyKeyboardMarkup);
                    sendResponse(message);
                }
            }).start();
        }
    }

    /**
     * Метод для отправки ответа пользователю
     * @param message   Ответ
     */
    private void sendResponse(SendMessage message) {
        try {
            EventQueue.invokeAndWait(() -> {
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
