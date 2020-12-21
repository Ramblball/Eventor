package view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import view.dialog.Dialog;

/**
 * Класс инициализации и взаимодействия с телеграм ботом
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final static String botName = "eventor_oop_bot";
    private static final Logger logger = LogManager.getLogger(TelegramBot.class);
    public static ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();


    /**
     * Метод для получения имени бота
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
        String botToken;
        try {
            BufferedReader br = new BufferedReader(new FileReader("token.txt"));
            botToken = br.readLine();
        } catch (IOException e) {
            botToken = System.getenv("TOKEN");
        }
        return botToken;
    }

    /**
     * Получает сообщение от пользователя и отдаёт на обработку в DialogTransmitter
     * @param update    Сообщение от пользователя в виде Update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            new Thread(() -> {
                synchronized (update.getMessage().getFrom()) {
                    if (update.getMessage().hasText()) {
                        logger.info(update.getMessage().getFrom().getFirstName() + " => " + update.getMessage().getText());
                    }
                    SendMessage message = new SendMessage()
                            .setChatId(update.getMessage().getChatId())
                            .setText(Dialog.get(update.getMessage()))
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
    private synchronized void sendResponse(SendMessage message) {
        try {
            EventQueue.invokeAndWait(() -> {
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            logger.error("Ошибка при отправке данных", e);
        }
    }
}
