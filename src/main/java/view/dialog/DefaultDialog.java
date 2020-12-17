package view.dialog;

import controller.Keywords;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.User;
import view.*;
import view.commands.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DefaultDialog implements IDialog{

    // Метод для посторения диалога при неизвестном запросе
    Default() {
        @Override
        public String send(Message message) {
            Progress progress = UserStateCache.getProgress(message.getFrom());
            if (progress == null) {
                return Unknown.send(message);
            }
            String operation = progress.getMessage().getOperation();
            return DefaultMap.values
                    .getOrDefault(operation, Unknown)
                    .send(message);
        }
    },

    // Метод описывающий диалог работы со всеми полями мероприятий
    EventWithAllFields("Создать", "Создать " + Emoji.PLUS, Emoji.PLUS,
            "Изменить",  "Изменить " + Emoji.PENCIL, Emoji.PENCIL,
            "По параметрам", "По параметрам " + Emoji.NIB, Emoji.NIB) {
        @Override
        public String send(Message message) {
            User user = message.getFrom();
            String received = message.getText();
            List<MessageEntity> entities = message.getEntities();
            Progress progress = UserStateCache.getProgress(user);
            telegramMessage = progress.getMessage();
            switch (progress.getIndex()) {
                case 0:
                    telegramMessage.setEventName(received);
                    UserStateCache.setProgress(user, new Progress(telegramMessage, 1));
                    return "Введите время мероприятия в формате " + Keywords.DATE_TIME_FORMAT;
                case 1:
                    telegramMessage.setEventTime(received);
                    UserStateCache.setProgress(user, new Progress(telegramMessage, 2));
                    return "Введите место мероприятия";
                case 2:
                    telegramMessage.setEventPlace(
                            entities != null
                                    ? formatter.applyFormatting(received, entities)
                                    : received);
                    UserStateCache.setProgress(user, new Progress(telegramMessage, 3));
                    return "Введите описание мероприятия";
                case 3:
                    telegramMessage.setEventDescription(
                            entities != null
                                    ? formatter.applyFormatting(received, entities)
                                    : received);
                    UserStateCache.setProgress(user, null);
                    telegramKeyboard.createOperationMenu();
                    TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
                    return Command
                            .getCommands()
                            .get(telegramMessage.getOperation())
                            .execute(telegramMessage);
                default:
                    return Keywords.EXCEPTION;
            }
        }
    },

    // Метод для ответа на ввод названия мероприятия
    FindName("По имени", "По имени " + Emoji.SPEECH_BALLOON, Emoji.SPEECH_BALLOON) {
        @Override
        public String send(Message message) {
            String received = message.getText();
            telegramMessage = UserStateCache.getProgress(message.getFrom()).getMessage();
            telegramMessage.setEventName(received);
            telegramKeyboard.createFindMenu();
            return Command
                    .getCommands()
                    .get(telegramMessage.getOperation())
                    .execute(telegramMessage);
        }
    },

    // Метод для посторения диалога управления подпиской
    EventSubscribe("Подписаться", "Подписаться " + Emoji.CHECK, Emoji.CHECK,
            "Отписаться", "Отписаться " + Emoji.X_MARK, Emoji.X_MARK,
            "Удалить", "Удалить " + Emoji.MINUS, Emoji.MINUS) {
        @Override
        public String send(Message message) {
            String received = message.getText();
            telegramMessage = UserStateCache.getProgress(message.getFrom()).getMessage();
            telegramKeyboard.createOperationMenu();
            telegramMessage.setEventName(received);
            return Command
                    .getCommands()
                    .get(telegramMessage.getOperation())
                    .execute(telegramMessage);
        }
    },

    // Метод для посторения диалога при неизвестном запросе
    Unknown() {
        @Override
        public String send(Message message) {
            return Command.valueOf("unknown").execute(new TelegramMessage(message.getFrom()));
        }
    };

    protected TelegramKeyboard telegramKeyboard = new TelegramKeyboard();
    protected TelegramMessage telegramMessage;
    protected final Formatter formatter = new Formatter();
    protected static class DefaultMap {
        private final static Map<String, IDialog> values = new HashMap<>();
    }

    public abstract String send(Message message);

    DefaultDialog(String... request) {
        for (String value : request) {
            DefaultMap.values.put(value, this);
        }
    }
}
