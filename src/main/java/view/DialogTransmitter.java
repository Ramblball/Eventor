package view;

import controller.Keywords;
import database.utils.QueryLiterals;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import view.commands.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс для предоставления диалогов
 */
public class DialogTransmitter {
    private final TelegramKeyboard telegramKeyboard = new TelegramKeyboard();

    private final Message message = new Message();
    private static final HashMap<String, Command> hashMap = new HashMap<>();

    DialogTransmitter() {
        setCommands();
    }

    /**
     * Задание параметров для клавиатуры
     */
    private void createKeyboard() {

    }

    /**Скрывает, открывает диалоги, сохраняет прогресс диалога
     * @param user     текущий пользователь
     * @param received полученное сообщение
     * @return ответ пользователю
     */
    public String getMessage(User user, String received) {
        if (TelegramBot.userProgress.get(user) == null) {
            TelegramBot.userProgress.put(user, new Progress());
        }
        createKeyboard();
        message.setUser(user);
        switch (received) {
            case "Привет":
            case "/start":
            case "Мои подписки":
            case "Созданные мероприятия":
            case "Помощь":
                telegramKeyboard.createMainMenu();
                return hashMap.get(received).execute(message);
            case "Назад":
                telegramKeyboard.createMainMenu();
                return "Выберите пункт меню";
            case "Управление подписками":
                telegramKeyboard.createOperationMenu();
                return "Что вы хотите сделать?";
            case "Поиск":
                telegramKeyboard.createFindMenu();
                return "Как вы хотите искать?";
            case "Создать":
            case "Изменить":
            case "Удалить":
            case "Подписаться":
            case "Отписаться":
                telegramKeyboard.hideMenu();
                TelegramBot.userProgress.get(user).operation = received;
                TelegramBot.userProgress.get(user).count = 0;
                return "Введите название мероприятия";
            case "По имени":
            case "По параметрам":
                telegramKeyboard.hideMenu();
                TelegramBot.userProgress.get(user).operation = received;
                TelegramBot.userProgress.get(user).count = 0;
                return "Введите имя искомого мероприятия";
            default:
                switch (TelegramBot.userProgress.get(user).operation) {
                    case "Создать":
                    case "Изменить":
                        if (TelegramBot.userProgress.get(user).count == 0) {
                            message.setEventName(received);
                            TelegramBot.userProgress.get(user).count++;
                            return "Введите время мероприятия в формате " + Keywords.DATE_TIME_FORMAT;
                        }
                        if (TelegramBot.userProgress.get(user).count == 1) {
                            message.setEventTime(received);
                            TelegramBot.userProgress.get(user).count++;
                            return "Введите место мероприятия";
                        }
                        if (TelegramBot.userProgress.get(user).count == 2) {
                            message.setEventPlace(received);
                            TelegramBot.userProgress.get(user).count++;
                            return "Введите описание мероприятия";
                        }
                        message.setEventDescription(received);
                        TelegramBot.userProgress.get(user).count = 0;
                        telegramKeyboard.createOperationMenu();
                        return hashMap.get(TelegramBot.userProgress.get(user).operation).execute(message);
                    case "По имени":
                        message.setEventName(received);
                        telegramKeyboard.createFindMenu();
                        return hashMap.get(TelegramBot.userProgress.get(user).operation).execute(message);
                    case "По параметрам":
                        if (TelegramBot.userProgress.get(user).count == 0) {
                            message.setEventName(received);
                            TelegramBot.userProgress.get(user).count++;
                            return "Введите время мероприятия в формате " + QueryLiterals.DATE_PATTERN;
                        }
                        if (TelegramBot.userProgress.get(user).count == 1) {
                            message.setEventTime(received);
                            TelegramBot.userProgress.get(user).count++;
                            return "Введите место мероприятия";
                        }
                        if (TelegramBot.userProgress.get(user).count == 2) {
                            message.setEventPlace(received);
                            TelegramBot.userProgress.get(user).count++;
                            return "Введите описание мероприятия";
                        }
                        message.setEventDescription(received);
                        telegramKeyboard.createOperationMenu();
                        TelegramBot.userProgress.get(user).count = 0;
                        telegramKeyboard.createFindMenu();
                        return hashMap.get(TelegramBot.userProgress.get(user).operation).execute(message);
                    case "Удалить":
                    case "Подписаться":
                    case "Отписаться":
                        telegramKeyboard.createOperationMenu();
                        return hashMap.get(TelegramBot.userProgress.get(user).operation).execute(message);
                    default:
                        telegramKeyboard.createMainMenu();
                        return new Unknown().execute(new Message());
                }
        }
    }



    public void setCommands() {
        hashMap.put("Создать", new CreateEventCommand());
        hashMap.put("Помощь", new HelpCommand());
        hashMap.put("Удалить", new RemoveEventCommand());
        hashMap.put("Изменить", new UpdateEventCommand());
        hashMap.put("Подписаться", new SubscribeCommand());
        hashMap.put("Отписаться", new UnsubscribeCommand());
        hashMap.put("Мои подписки", new SubscribesGetCommand());
        hashMap.put("Созданные мероприятия", new OwnEventsGetCommand());
        hashMap.put("По имени", new EventFindCommand());
        hashMap.put("По параметрам", new EventParametersFindCommand());
    }
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return telegramKeyboard.getReplyKeyboardMarkup();
    }
}
