package view;

import controller.Keywords;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import view.commands.Help;
import view.commands.Unknown;
import view.commands.UserCreating;

import java.util.ArrayList;

/**
 * Класс для предоставления диалогов
 */
public class DialogTransmitter {
    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private final ArrayList<KeyboardRow> keyboard = new ArrayList<>();
    private final KeyboardRow firstRow = new KeyboardRow();
    private final KeyboardRow secondRow = new KeyboardRow();
    private final KeyboardRow thirdRow = new KeyboardRow();
    private final KeyboardRow fourthRow = new KeyboardRow();
    private final Provider provider = new Provider();
    private final Message message = new Message();

    /**
     * Задание параметров для клавиатуры
     */
    private void createKeyboard(){
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
    }

    /** Скрывает, открывает диалоги, сохраняет прогресс диалога
     * @param user текущий пользователь
     * @param received полученное сообщение
     * @return ответ пользователю
     */
    public String getMessage(User user, String received) {
        if(TelegramBot.userProgress.isEmpty()){
            TelegramBot.userProgress.put(user, new Progress());
        }
        createKeyboard();
        message.setUser(user);
        switch (received) {
            case "Привет":
            case "/start":
                createMainMenu();
                return new UserCreating().execute(message);
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
                hideMenu();
                TelegramBot.userProgress.get(user).operation = received;
                TelegramBot.userProgress.get(user).count = 0;
                return "Введите название мероприятия";
            case "Удалить":
            case "Подписаться":
            case "Отписаться":
                hideMenu();
                TelegramBot.userProgress.get(user).operation = received;
                TelegramBot.userProgress.get(user).count = 0;
                return "Введите id мероприятия";
            case "Мои подписки":
            case "Созданные мероприятия":
                createMainMenu();
                return provider.execute(received, message);
            case "По имени":
            case "По параметрам":
                hideMenu();
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
                            return "Введите время мероприятия в формате " + Keywords.dateTimeFormat;
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
                        createOperationMenu();
                        return provider.execute(TelegramBot.userProgress.get(user).operation, message);
                    case "По имени":
                        message.setEventName(received);
                        createFindMenu();
                        return provider.execute(TelegramBot.userProgress.get(user).operation, message);
                    case "По параметрам":
                        if (TelegramBot.userProgress.get(user).count == 0) {
                            message.setEventName(received);
                            TelegramBot.userProgress.get(user).count++;
                            return "Введите время мероприятия в формате " + Keywords.dateTimeFormat;
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
                        createOperationMenu();
                        TelegramBot.userProgress.get(user).count = 0;
                        createFindMenu();
                        return provider.execute(TelegramBot.userProgress.get(user).operation, message);
                    case "Удалить":
                    case "Подписаться":
                    case "Отписаться":
                        createOperationMenu();
                        return provider.execute(TelegramBot.userProgress.get(user).operation, message);
                    default:
                        createMainMenu();
                        return new Unknown().execute(new Message());
                }
        }
    }

    /**
     * Скрытие меню, установка кнопки возврата
     */
    private void hideMenu() {
        clearKeyboardRows();
        firstRow.add("Назад");
        keyboard.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

    }

    /**
     * Очистка клавиатуры
     */
    private void clearKeyboardRows() {
        keyboard.clear();
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();
        fourthRow.clear();
    }

    /**
     * Создание кнопок поиска
     */
    private void createFindMenu() {
        clearKeyboardRows();
        firstRow.add("По имени");
        firstRow.add("По параметрам");
        secondRow.add("Назад");
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    /**
     * Создание кнопок основных действий
     */
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

    /**
     * Создание кнопок с разделами
     */
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
     * @return разметка клавиатуры
     */
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return replyKeyboardMarkup;
    }
}
