package view.commands;

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchFeed;
import at.mukprojects.giphy4j.exception.GiphyException;
import controller.EventController;
import controller.UserController;
import database.model.Event;
import database.utils.EventQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.Emoji;
import view.TelegramMessage;
import view.dialog.DefaultDialog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    //Команда для получения GIF по запросу пользователя
    FindGIF("Найти GIF", "Найти GIF " + Emoji.CAMERA, Emoji.CAMERA){
        @Override
        public String execute(TelegramMessage telegramMessage){
            String apiToken;
            try {
                BufferedReader br = new BufferedReader(new FileReader("apitoken.txt"));
                apiToken = br.readLine();
            } catch (IOException e) {
                apiToken = System.getenv("API_TOKEN");
            }
            Giphy giphy = new Giphy(apiToken);
            SearchFeed feed = null;
            String description = eventController.getEventDescription(telegramMessage.getEventName());
            if (description == null) {
                return "Мероприятие не найдено";
            }
            try {
                feed = giphy.search(description, 1, 0);
            } catch (GiphyException e) {
                logger.error(e.getMessage(), e);
            }
            assert feed != null;
            if (feed.getDataList().isEmpty())
                return "На данный запрос не найдено GIF";
            return feed.getDataList().get(0).getImages().getOriginal().getUrl();
        }
    },

    // Команда ошибки при вызове несуществующей команды
    Unknown() {
        @Override
        public String execute(TelegramMessage telegramMessage) {
            return "Неизвестная команда. Введите \"Помощь\"";
        }
    };

    protected EventController eventController = new EventController();
    protected UserController userController = new UserController();
    private static final Logger logger = LogManager.getLogger(DefaultDialog.class);

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
