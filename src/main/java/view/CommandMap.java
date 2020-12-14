package view;

import view.commands.*;

import java.util.HashMap;

/**
 * Класс, который содержит
 */
public class CommandMap {
    private final HashMap<String, Command> commandMap = new HashMap<>();

    public CommandMap(){
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

    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
