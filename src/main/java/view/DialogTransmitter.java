package view;

import controller.Keywords;
import database.utils.QueryLiterals;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import view.commands.*;

import java.util.HashMap;
import java.util.List;

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
    public String getMessage(User user, String received, List<MessageEntity> entities) {
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
            case "Помощь " + Emoji.INFO:
            case "Мои подписки " + Emoji.MEMO:
            case "Созданные мероприятия " + Emoji.BOOKS:
            case Emoji.INFO:
            case Emoji.BOOKS:
            case Emoji.MEMO:
                telegramKeyboard.createMainMenu();
                return commandMap.get(received).execute(message);
            case "Назад":
            case "Назад " + Emoji.BACK:
            case Emoji.BACK:
                telegramKeyboard.createMainMenu();
                return "Выберите пункт меню";
            case "Управление подписками":
            case "Управление подписками " + Emoji.WRENCH:
            case Emoji.WRENCH:
                telegramKeyboard.createOperationMenu();
                return "Что вы хотите сделать?";
            case "Поиск":
            case Emoji.MAGNIFYING_GLASS:
            case "Поиск " + Emoji.MAGNIFYING_GLASS:
                telegramKeyboard.createFindMenu();
                return "Как вы хотите искать?";
            case "Создать":
            case "Изменить":
            case "Удалить":
            case "Подписаться":
            case "Отписаться":
            case "Создать " + Emoji.PLUS:
            case "Изменить " + Emoji.PENCIL:
            case "Удалить " + Emoji.MINUS:
            case "Подписаться " + Emoji.CHECK:
            case "Отписаться " + Emoji.X_MARK:
            case Emoji.PLUS:
            case Emoji.MINUS:
            case Emoji.PENCIL:
            case Emoji.X_MARK:
            case Emoji.CHECK:
                telegramKeyboard.hideMenu();
                message.setOperation(received);
                userState.setProgress(user, new Progress(message, 0));
                return "Введите название мероприятия";
            case "По имени":
            case "По параметрам":
            case "По имени " + Emoji.SPEECH_BALLOON:
            case "По параметрам " + Emoji.NIB:
            case Emoji.SPEECH_BALLOON:
            case Emoji.NIB:
                telegramKeyboard.hideMenu();
                message.setOperation(received);
                userState.setProgress(user, new Progress(message, 0));
                return "Введите имя искомого мероприятия";
            case "На текущей неделе":
            case "На текущей неделе " + Emoji.CALENDAR:
            case Emoji.CALENDAR:
                telegramKeyboard.createFindMenu();
                return commandMap.get(received).execute(message);
            default:
                if (userState.getProgress(user) == null) {
                    telegramKeyboard.createMainMenu();
                    return new Unknown().execute(new Message(user));
                }
                switch (userState.getProgress(user).getMessage().getOperation()) {
                    case "Создать":
                    case "Изменить":
                    case "Изменить " + Emoji.PENCIL:
                    case "Создать " + Emoji.PLUS:
                    case Emoji.PLUS:
                    case Emoji.PENCIL:
                        if (userState.getProgress(user).getIndex() == 0) {
                            message.setEventName(applyFormatting(received, entities));
                            userState.setProgress(user, new Progress(message, 1));
                            return "Введите время мероприятия в формате " + Keywords.DATE_TIME_FORMAT;
                        }
                        if (userState.getProgress(user).getIndex() == 1) {
                            message.setEventTime(applyFormatting(received, entities));
                            userState.setProgress(user, new Progress(message, 2));
                            return "Введите место мероприятия";
                        }
                        if (userState.getProgress(user).getIndex() == 2) {
                            message.setEventPlace(applyFormatting(received, entities));
                            userState.setProgress(user, new Progress(message, 3));
                            return "Введите описание мероприятия";
                        }
                        if (userState.getProgress(user).getIndex() == 3) {
                            message.setEventDescription(applyFormatting(received, entities));
                            userState.setProgress(user, null);
                            telegramKeyboard.createOperationMenu();
                            return commandMap.get(message.getOperation()).execute(message);
                        }
                    case "По имени":
                    case "По имени " + Emoji.SPEECH_BALLOON:
                    case Emoji.SPEECH_BALLOON:
                        message.setEventName(applyFormatting(received, entities));
                        telegramKeyboard.createFindMenu();
                        return commandMap.get(message.getOperation()).execute(message);
                    case "По параметрам":
                    case "По параметрам " + Emoji.NIB:
                    case Emoji.NIB:
                        if (userState.getProgress(user).getIndex() == 0) {
                            message.setEventName(applyFormatting(received, entities));
                            userState.setProgress(user, new Progress(message, 1));
                            return "Введите время мероприятия в формате " + QueryLiterals.DATE_PATTERN;
                        }
                        if (userState.getProgress(user).getIndex() == 1) {
                            message.setEventTime(applyFormatting(received, entities));
                            userState.setProgress(user, new Progress(message, 2));
                            return "Введите место мероприятия";
                        }
                        if (userState.getProgress(user).getIndex() == 2) {
                            message.setEventPlace(applyFormatting(received, entities));
                            userState.setProgress(user, new Progress(message, 3));
                            return "Введите описание мероприятия";
                        }
                        if (userState.getProgress(user).getIndex() == 3) {
                            message.setEventDescription(applyFormatting(received, entities));
                            telegramKeyboard.createOperationMenu();
                            userState.setProgress(user, null);
                            return commandMap.get(message.getOperation()).execute(message);
                        }
                    case "Удалить":
                    case "Подписаться":
                    case "Отписаться":
                    case "Удалить " + Emoji.MINUS:
                    case "Подписаться " + Emoji.CHECK:
                    case "Отписаться " + Emoji.X_MARK:
                    case Emoji.MINUS:
                    case Emoji.CHECK:
                    case Emoji.X_MARK:
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
        commandMap.put("На текущей неделе", new EventWeekFindCommand());

        commandMap.put("Создать " + Emoji.PLUS, new CreateEventCommand());
        commandMap.put("Помощь " + Emoji.INFO, new HelpCommand());
        commandMap.put("Удалить " + Emoji.MINUS, new RemoveEventCommand());
        commandMap.put("Изменить " + Emoji.PENCIL, new UpdateEventCommand());
        commandMap.put("Подписаться " + Emoji.CHECK, new SubscribeCommand());
        commandMap.put("Отписаться " + Emoji.X_MARK, new UnsubscribeCommand());
        commandMap.put("Мои подписки " + Emoji.BOOKS, new SubscribesGetCommand());
        commandMap.put("Созданные мероприятия " + Emoji.MEMO, new OwnEventsGetCommand());
        commandMap.put("По имени " + Emoji.SPEECH_BALLOON, new EventFindCommand());
        commandMap.put("По параметрам " + Emoji.NIB, new EventParametersFindCommand());
        commandMap.put("На текущей неделе " + Emoji.CALENDAR, new EventWeekFindCommand());

        commandMap.put(Emoji.PLUS, new CreateEventCommand());
        commandMap.put(Emoji.INFO, new HelpCommand());
        commandMap.put(Emoji.MINUS, new RemoveEventCommand());
        commandMap.put(Emoji.PENCIL, new UpdateEventCommand());
        commandMap.put(Emoji.CHECK, new SubscribeCommand());
        commandMap.put(Emoji.X_MARK, new UnsubscribeCommand());
        commandMap.put(Emoji.BOOKS, new SubscribesGetCommand());
        commandMap.put(Emoji.MEMO, new OwnEventsGetCommand());
        commandMap.put(Emoji.SPEECH_BALLOON, new EventFindCommand());
        commandMap.put(Emoji.NIB, new EventParametersFindCommand());
        commandMap.put(Emoji.CALENDAR, new EventWeekFindCommand());
    }

    public String applyFormatting(String text, List<MessageEntity> entities){
        if (entities.isEmpty())
            return text;
        var sb = new StringBuilder();
        int shift = 0;
        sb.append(text);
        for (MessageEntity entity:entities) {
            switch (entity.getType()) {
                case "bold":
                    sb.insert(entity.getOffset() + shift, "<b>");
                    shift += 3;
                    sb.insert(entity.getLength() + shift, "</b>");
                    shift += 4;
                    break;
                case "italic":
                    sb.insert(entity.getOffset() + shift, "<i>");
                    shift += 3;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</i>");
                    shift += 4;
                    break;
                case "underline":
                    sb.insert(entity.getOffset() + shift, "<u>");
                    shift += 3;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</u>");
                    shift += 4;
                    break;
                case "strikethrough":
                    sb.insert(entity.getOffset() + shift, "<s>");
                    shift += 3;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</s>");
                    shift += 4;
                    break;
                case "code":
                    sb.insert(entity.getOffset() + shift, "<code>");
                    shift += 6;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</code>");
                    shift += 7;
                    break;
            }
        }
        return sb.toString();
    }

    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return telegramKeyboard.getReplyKeyboardMarkup();
    }
}
