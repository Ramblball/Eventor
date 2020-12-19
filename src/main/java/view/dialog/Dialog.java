package view.dialog;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import view.*;
import view.Formatter;
import view.commands.Command;

import java.util.*;

public enum Dialog implements IDialog {

    // Метод для создания главного меню
    MainMenu("Привет", "/start", "Помощь", "Помощь " + Emoji.INFO, Emoji.INFO,
            "Созданные мероприятия", "Созданные мероприятия " + Emoji.MEMO, Emoji.MEMO,
            "Мои подписки", "Мои подписки " + Emoji.BOOKS, Emoji.BOOKS) {
        @Override
        public String send(Message message) {
            telegramMessage = UserStateCache.checkProgress(message);
            telegramKeyboard.createMainMenu();
            TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
            return Command
                    .getCommands()
                    .get(message.getText())
                    .execute(telegramMessage);
        }
    },

    // Метод для создания меню управления подписками
    EventMenu("Управление подписками", "Управление подписками " + Emoji.WRENCH, Emoji.WRENCH) {
        @Override
        public String send(Message message) {
            telegramMessage = UserStateCache.checkProgress(message);
            telegramKeyboard.createOperationMenu();
            TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
            return "Что вы хотите сделать?";
        }
    },

    // Метод для создания меню поиска
    FindMenu("Поиск", "Поиск " + Emoji.MAGNIFYING_GLASS, Emoji.MAGNIFYING_GLASS) {
        @Override
        public String send(Message message) {
            telegramMessage = UserStateCache.checkProgress(message);
            telegramKeyboard.createFindMenu();
            TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
            return "Как вы хотите искать?";
        }
    },

    // Метод для получения мероприятий, не требующих ввод данных
    FindWeek("На текущей неделе", "На текущей неделе " + Emoji.CALENDAR, Emoji.CALENDAR,
            "Случайное" , "Случайное " + Emoji.RANDOM, Emoji.RANDOM) {
        @Override
        public String send(Message message) {
            telegramMessage = UserStateCache.checkProgress(message);
            String received = message.getText();
            telegramKeyboard.createFindMenu();
            TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
            return Command
                    .getCommands()
                    .get(received)
                    .execute(telegramMessage);
        }
    },

    // Метод для ответа на ввод названия мероприятия
    EventName("Создать", "Создать " + Emoji.PLUS, Emoji.PLUS,
            "Изменить", "Изменить " + Emoji.PENCIL, Emoji.PENCIL,
            "Удалить", "Удалить " + Emoji.MINUS, Emoji.MINUS,
            "Подписаться", "Подписаться " + Emoji.CHECK, Emoji.CHECK,
            "Отписаться", "Отписаться " + Emoji.X_MARK, Emoji.X_MARK,
            "По имени", "По имени " + Emoji.SPEECH_BALLOON, Emoji.SPEECH_BALLOON,
            "По параметрам", "По параметрам " + Emoji.NIB, Emoji.NIB) {
        @Override
        public String send(Message message) {
            telegramMessage = UserStateCache.checkProgress(message);
            String received = message.getText();
            User user = message.getFrom();
            telegramKeyboard.hideMenu();
            telegramMessage.setOperation(received);
            UserStateCache.setProgress(user, new Progress(telegramMessage, 0));
            TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
            return "Введите название мероприятия";
        }
    },

    // Метод для посторения диалога отмены действия
    ReturnBack("Назад", "Назад " + Emoji.BACK, Emoji.BACK) {
        @Override
        public String send(Message message) {
            telegramMessage = UserStateCache.checkProgress(message);
            telegramKeyboard.createMainMenu();
            UserStateCache.setProgress(message.getFrom(), null);
            TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
            return "Выберите пункт меню";
        }
    },

    // Метод для посторения диалога при неизвестном запросе
    Unknown() {
        @Override
        public String send(Message message) {
            return Command.valueOf("unknown").execute(new TelegramMessage(message.getFrom()));
        }
    };

    protected TelegramMessage telegramMessage;
    protected final TelegramKeyboard telegramKeyboard = new TelegramKeyboard();
    protected final Formatter formatter = new Formatter();
    protected static class MainMap {
        private final static Map<String, IDialog> values = new HashMap<>();
    }

    public abstract String send(Message message);

    Dialog(String... request) {
        for (String value : request) {
            MainMap.values.put(value, this);
        }
    }

    public static String get(Message message) {
        return MainMap.values.getOrDefault(message.getText(), DefaultDialog.Default).send(message);
    }
}
