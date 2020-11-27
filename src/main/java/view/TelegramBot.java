package view;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс инициализации и взаимодействия с телеграм ботом
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final static String botName = "eventor_oop_bot";

    /** Создаёт прикреплённую клавиатуру к каждому сообщению
     * @return Прикреплённую к каждому сообщению клавиатуру след. вида:
     *                           Get help                             |
     *     Create event    |    Subscribe     |     Unsubscribe       |
     *     Find event by name      |        Find events by parameters |
     *     Remove event by id      |        Update event              |
     */
    private InlineKeyboardMarkup setKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        List<InlineKeyboardButton> fifthRow = new ArrayList<>();

        firstRow.add(Buttons.getHelp);
        thirdRow.add(Buttons.createEvent);
        thirdRow.add(Buttons.signUp);
        thirdRow.add(Buttons.signOut);
        fourthRow.add(Buttons.find);
        fourthRow.add(Buttons.findParams);
        fifthRow.add(Buttons.remove);
        fifthRow.add(Buttons.update);

        rowsInline.add(firstRow);
        rowsInline.add(thirdRow);
        rowsInline.add(fourthRow);
        rowsInline.add(fifthRow);

        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

    /**
     * Возрвращает имя бота
     * @return Имя телеграм бота
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Возвращает токен бота из текстового файла
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
     * @param update Сообщение от пользователя в виде Update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String address = "@" +
                    botName +
                    " ";
            var provider = new Provider(update.getMessage().getChat().getUserName(), update.getMessage().getText().replace(address, ""));
            var answer = provider.execute();
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage()
                            .getChatId()).setText(answer);
            message.setReplyMarkup(setKeyboard());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
