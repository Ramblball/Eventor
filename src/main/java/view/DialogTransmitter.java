package view;

import controller.Keywords;
import database.utils.QueryLiterals;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import view.commands.*;

import java.util.HashMap;

/**
 * Класс для предоставления диалогов
 */
public class DialogTransmitter {
    private final TelegramKeyboard telegramKeyboard = new TelegramKeyboard();
    private static final HashMap<String, Command> commandMap = new HashMap<>();
    private static final UserStateCache userState = new UserStateCache();

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
        createKeyboard();
        Message message;
        if (userState.getProgress(user) != null) {
            message = userState.getProgress(user).getMessage();
        } else {
            message = new Message(user);
        }
        switch (received) {
            case "Привет":
            case "/start":
            case "Мои подписки":
            case "Созданные мероприятия":
            case "Помощь":
                telegramKeyboard.createMainMenu();
                return commandMap.get(received).execute(message);
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
                message.setOperation(received);
                userState.setProgress(user, new Progress(message, 0));
                return "Введите название мероприятия";
            case "По имени":
            case "По параметрам":
                telegramKeyboard.hideMenu();
                message.setOperation(received);
                userState.setProgress(user, new Progress(message, 0));
                return "Введите имя искомого мероприятия";
            default:
                if (userState.getProgress(user) == null) {
                    telegramKeyboard.createMainMenu();
                    return new Unknown().execute(new Message(user));
                }
                switch (userState.getProgress(user).getMessage().getOperation()) {
                    case "Создать":
                    case "Изменить":
                        if (userState.getProgress(user).getIndex() == 0) {
                            message.setEventName(received);
                            userState.setProgress(user, new Progress(message, 1));
                            return "Введите время мероприятия в формате " + Keywords.DATE_TIME_FORMAT;
                        }
                        if (userState.getProgress(user).getIndex() == 1) {
                            message.setEventTime(received);
                            userState.setProgress(user, new Progress(message, 2));
                            return "Введите место мероприятия";
                        }
                        if (userState.getProgress(user).getIndex() == 2) {
                            message.setEventPlace(received);
                            userState.setProgress(user, new Progress(message, 3));
                            return "Введите описание мероприятия";
                        }
                        if (userState.getProgress(user).getIndex() == 3) {
                            message.setEventDescription(received);
                            userState.setProgress(user, null);
                            telegramKeyboard.createOperationMenu();
                            return commandMap.get(message.getOperation()).execute(message);
                        }
                    case "По имени":
                        message.setEventName(received);
                        telegramKeyboard.createFindMenu();
                        return commandMap.get(message.getOperation()).execute(message);
                    case "По параметрам":
                        if (userState.getProgress(user).getIndex() == 0) {
                            message.setEventName(received);
                            userState.setProgress(user, new Progress(message, 1));
                            return "Введите время мероприятия в формате " + QueryLiterals.DATE_PATTERN;
                        }
                        if (userState.getProgress(user).getIndex() == 1) {
                            message.setEventTime(received);
                            userState.setProgress(user, new Progress(message, 2));
                            return "Введите место мероприятия";
                        }
                        if (userState.getProgress(user).getIndex() == 2) {
                            message.setEventPlace(received);
                            userState.setProgress(user, new Progress(message, 3));
                            return "Введите описание мероприятия";
                        }
                        if (userState.getProgress(user).getIndex() == 3) {
                            message.setEventDescription(received);
                            telegramKeyboard.createOperationMenu();
                            userState.setProgress(user, null);
                            return commandMap.get(message.getOperation()).execute(message);
                        }
                    case "Удалить":
                    case "Подписаться":
                    case "Отписаться":
                        telegramKeyboard.createOperationMenu();
                        return commandMap.get(message.getOperation()).execute(message);
                    default:
                        telegramKeyboard.createMainMenu();
                        return new Unknown().execute(new Message(user));
                }
        }
    }

    public void setCommands() {
        commandMap.put("/start", new UserCreateCommand());
        commandMap.put("Привет", new UserCreateCommand());
        commandMap.put("Создать", new CreateEventCommand());
        commandMap.put("Помощь", new HelpCommand());
        commandMap.put("Удалить", new RemoveEventCommand());
        commandMap.put("Изменить", new UpdateEventCommand());
        commandMap.put("Подписаться", new SubscribeCommand());
        commandMap.put("Отписаться", new UnsubscribeCommand());
        commandMap.put("Мои подписки", new SubscribesGetCommand());
        commandMap.put("Созданные мероприятия", new OwnEventsGetCommand());
        commandMap.put("По имени", new EventFindCommand());
        commandMap.put("По параметрам", new EventParametersFindCommand());
    }

    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return telegramKeyboard.getReplyKeyboardMarkup();
    }
}
