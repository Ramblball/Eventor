package view.commands;

import controller.EventController;
import controller.UserController;
import database.utils.EventQuery;
import view.Emoji;
import view.TelegramMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum для описания команд пользователя
 */
public enum Command implements ICommand {

    // Команда вызова помощи
    Help("Помощь", "Помощь " + Emoji.INFO, Emoji.INFO) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController.getHelp();
        }
    },

    // Команда сохранения информации о пользователе
    UserCreate("/start", "Привет") {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return userController.create(telegramMessage.getUser());
        }
    },

    // Команда создания мероприятия
    EventCreate("Создать", "Создать " + Emoji.PLUS, Emoji.PLUS) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController
                    .create(telegramMessage.getUser().getId(),
                            telegramMessage.getEventName(),
                            telegramMessage.getEventTime(),
                            telegramMessage.getEventPlace(),
                            telegramMessage.getEventLatitude(),
                            telegramMessage.getEventLongitude(),
                            telegramMessage.getEventLimit(),
                            telegramMessage.getEventDescription());
        }
    },

    // Команда обновления мероприятия
    EventUpdate("Изменить", "Изменить " + Emoji.PENCIL, Emoji.PENCIL) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController
                    .update(telegramMessage.getUser().getId(),
                            telegramMessage.getEventName(),
                            telegramMessage.getEventTime(),
                            telegramMessage.getEventPlace(),
                            telegramMessage.getEventLatitude(),
                            telegramMessage.getEventLongitude(),
                            telegramMessage.getEventLimit(),
                            telegramMessage.getEventDescription());
        }
    },

    // Команда поиска мероприятия по названию
    EventFind("По имени", "По имени " + Emoji.SPEECH_BALLOON, Emoji.SPEECH_BALLOON) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController.findByName(telegramMessage.getEventName());
        }
    },

    // Команда поиска мероприятий по заданным параметрам
    EventFindFilter("По параметрам", "По параметрам " + Emoji.NIB, Emoji.NIB) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            EventQuery eventQuery = new EventQuery();
            eventQuery.setName(telegramMessage.getEventName());
            eventQuery.setDescription(telegramMessage.getEventDescription());
            eventQuery.setTime(telegramMessage.getEventTime());
            eventQuery.setPlace(telegramMessage.getEventLatitude(), telegramMessage.getEventLongitude());
            return eventController.findWithFilter(eventQuery);
        }
    },

    // Команда поиска мероприятий за текущую неделю
    EventFindWeek("На текущей неделе", "На текущей неделе " + Emoji.CALENDAR, Emoji.CALENDAR) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController.findForTheCurrentWeek();
        }
    },

    EventFindRandom("Случайное" , "Случайное " + Emoji.RANDOM, Emoji.RANDOM) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController.findRandom();
        }
    },

    // Команда удаления мероприятия
    EventRemove("Удалить", "Удалить " + Emoji.MINUS, Emoji.MINUS) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController
                    .remove(telegramMessage.getUser().getId(), telegramMessage.getEventName());
        }
    },

    // Команда подписки на мероприятие
    EventSubscribe("Подписаться", "Подписаться " + Emoji.CHECK, Emoji.CHECK) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController
                    .signIn(telegramMessage.getUser().getId(), telegramMessage.getEventName());
        }
    },

    // Команда отписки от мероприятия
    EventUnsubscribe("Отписаться", "Отписаться " + Emoji.X_MARK, Emoji.X_MARK) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController
                    .signOut(telegramMessage.getUser().getId(), telegramMessage.getEventName());
        }
    },

    // Команда для получения мероприятий созданных пользователем
    EventGetOwn("Созданные мероприятия", "Созданные мероприятия " + Emoji.MEMO, Emoji.MEMO) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController.getCreated(telegramMessage.getUser().getId());
        }
    },

    // Команда для получения мероприятий, на которые пользователь подписан
    EventGetSubscribes("Мои подписки", "Мои подписки " + Emoji.BOOKS, Emoji.BOOKS) {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return eventController.getSubscribes(telegramMessage.getUser().getId());
        }
    },

    // Команда ошибки при вызове несуществующей команды
    Unknown() {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return "Неивестная команда. Введите \"Помощь\"";
        }
    };

    protected EventController eventController = new EventController();
    protected UserController userController = new UserController();

    protected static class Commands {
        private final static Map<String, ICommand> values = new HashMap<>();
    }

    public abstract String execute(TelegramMessage message);

    Command(String... request) {
        for (String value : request) {
            Commands.values.put(value, this);
        }
    }

    public static Map<String, ICommand> getCommands() {
        return Commands.values;
    }
}
