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

/**
 * Класс инициализации и взаимодействия с телеграм ботом
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final static String botName = "eventor_oop_bot";
    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private final ArrayList<KeyboardRow> keyboard = new ArrayList<>();
    private final KeyboardRow firstRow = new KeyboardRow();
    private final KeyboardRow secondRow = new KeyboardRow();
    private final KeyboardRow thirdRow = new KeyboardRow();
    private final KeyboardRow fourthRow = new KeyboardRow();
    private final Provider provider = new Provider();
    private final Message message = new Message();
    private String operation = "";
    private int argumentCount = 0;

    private void createKeyboard(){
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
    }

    public String getMessage(User user, String received) {
        createKeyboard();
        message.setUser(user);
        switch (received) {
            case "Привет":
            case "/start":
            case "Назад":
                createMainMenu();
                return "Выберите пункт меню";
            case "Помощь":
                createMainMenu();
                return new Help().execute(new Message());
            case "Управление подписками":
                createOperationMenu();
                return "Что вы хотите сделать?";
            case "Поиск":
                createFindMenu();
                return "Как вы хотите искать?";
            case "Создать":
            case "Изменить":
                hideMenu(received);
                return "Введите название мероприятия";
            case "Удалить":
            case "Подписаться":
            case "Отписаться":
                hideMenu(received);
                return "Введите id мероприятия";
            case "Мои подписки":
            case "Созданные мероприятия":
                createMainMenu();
                return provider.execute(received, message);
            case "По имени":
            case "По параметрам":
                hideMenu(received);
                return "Введите имя искомого мероприятия";
            default:
                switch (operation) {
                    case "Создать":
                    case "Изменить":
                        if (argumentCount == 0) {
                            message.setEventName(received);
                            argumentCount++;
                            return "Введите время мероприятия в формате " + Keywords.dateTimeFormat;
                        }
                        if (argumentCount == 1) {
                            message.setEventTime(received);
                            argumentCount++;
                            return "Введите место мероприятия";
                        }
                        if (argumentCount == 2) {
                            message.setEventPlace(received);
                            argumentCount++;
                            return "Введите описание мероприятия";
                        }
                        message.setEventDescription(received);
                        argumentCount = 0;
                        createOperationMenu();
                        return provider.execute(operation, message);
                    case "По имени":
                        message.setEventName(received);
                        createFindMenu();
                        return provider.execute(operation, message);
                    case "По параметрам":
                        if (argumentCount == 0) {
                            message.setEventName(received);
                            argumentCount++;
                            return "Введите время мероприятия в формате " + Keywords.dateTimeFormat;
                        }
                        if (argumentCount == 1) {
                            message.setEventTime(received);
                            argumentCount++;
                            return "Введите место мероприятия";
                        }
                        if (argumentCount == 2) {
                            message.setEventPlace(received);
                            argumentCount++;
                            return "Введите описание мероприятия";
                        }
                        message.setEventDescription(received);
                        createOperationMenu();
                        argumentCount = 0;
                        createFindMenu();
                        return provider.execute(operation, message);
                    case "Удалить":
                    case "Подписаться":
                    case "Отписаться":
                        createOperationMenu();
                        return provider.execute(operation, message);
                    default:
                        createMainMenu();
                        return "Хз шо за команда";
                }
        }
    }

    private void hideMenu(String message) {
        clearKeyboardRows();
        firstRow.add("Назад");
        keyboard.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        operation = message;
        argumentCount = 0;
    }

    private void clearKeyboardRows() {
        keyboard.clear();
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();
        fourthRow.clear();
    }

    private void createFindMenu() {
        clearKeyboardRows();
        firstRow.add("По имени");
        firstRow.add("По параметрам");
        secondRow.add("Назад");
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private void createOperationMenu() {
        clearKeyboardRows();
        firstRow.add("Создать");
        firstRow.add("Изменить");
        firstRow.add("Удалить");
        thirdRow.add("Мои подписки");
        secondRow.add("Подписаться");
        secondRow.add("Отписаться");
        thirdRow.add("Созданные мероприятия");
        fourthRow.add("Назад");
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        keyboard.add(thirdRow);
        keyboard.add(fourthRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private void createMainMenu() {
        clearKeyboardRows();
        firstRow.add("Помощь");
        secondRow.add("Управление подписками");
        secondRow.add("Поиск");
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

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
                    .setText(getMessage(update.getMessage().getFrom(), update.getMessage().getText()));
            message.setReplyMarkup(replyKeyboardMarkup);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
