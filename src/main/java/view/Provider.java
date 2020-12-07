package view;

import view.commands.*;

import java.util.HashMap;

/**
 * Класс обработчик сообщений
 */
public class Provider {

    private static final HashMap<String, Command> hashMap = new HashMap<>();

    Provider(){
        hashMap.put("Создать", new CreateEventCommand());
        hashMap.put("Удалить", new RemoveEventCommand());
        hashMap.put("Изменить", new UpdateEventCommand());
        hashMap.put("Подписаться", new SubscribeCommand());
        hashMap.put("Отписаться", new UnsubscribeCommand());
        hashMap.put("Мои подписки", new SubscribesGetCommand());
        hashMap.put("Созданные мероприятия", new OwnEventsGetCommand());
        hashMap.put("По имени", new EventFindCommand());
        hashMap.put("По параметрам", new EventParametersFindCommand());
    }

    public String execute(String operation, Message message){
        return hashMap.get(operation).execute(message);
    }
}