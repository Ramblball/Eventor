package view;

import org.telegram.telegrambots.meta.api.objects.User;
import view.commands.*;

import java.util.HashMap;

/**
 * Класс обработчик сообщений
 */
public class Provider {

    private static final HashMap<String, Command> hashMap = new HashMap<>();

    Provider(){
        hashMap.put("Создать", new EventCreating());
        hashMap.put("Удалить", new RemovingEvent());
        hashMap.put("Изменить", new UpdatingEvent());
        hashMap.put("Подписаться", new Subscribing());
        hashMap.put("Отписаться", new Unsubscribing());
        hashMap.put("Мои подписки", new GetSubscribes());
        hashMap.put("Созданные мероприятия", new GetOwnEvents());
        hashMap.put("По имени", new FindingEvent());
        hashMap.put("По параметрам", new FindingEventParameters());
    }

    public String execute(String operation, Message message){
        return hashMap.get(operation).execute(message);
    }
}