package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.Emoji;
import view.UserStateCache;

import java.util.HashMap;

/**
 * Класс, который отвечает на запросы, не содержащихся в CommandMap
 */
public class DefaultAnswer extends Answer{
    private final HashMap<String, Answer> defaultAnswerHashMap = new HashMap<>();

    public DefaultAnswer(){
        defaultAnswerHashMap.put("Создать", new CreateEditFindParametersDefaultAnswer());
        defaultAnswerHashMap.put("Изменить", new CreateEditFindParametersDefaultAnswer());
        defaultAnswerHashMap.put("По параметрам", new CreateEditFindParametersDefaultAnswer());
        defaultAnswerHashMap.put("По имени", new FindNameDefaultAnswer());
        defaultAnswerHashMap.put("Подписаться", new SubscriptionDefaultAnswer());
        defaultAnswerHashMap.put("Отписаться", new SubscriptionDefaultAnswer());
        defaultAnswerHashMap.put("Удалить", new SubscriptionDefaultAnswer());

        defaultAnswerHashMap.put("Создать " + Emoji.PLUS, new CreateEditFindParametersDefaultAnswer());
        defaultAnswerHashMap.put("Изменить " + Emoji.PENCIL, new CreateEditFindParametersDefaultAnswer());
        defaultAnswerHashMap.put("По параметрам " + Emoji.NIB, new CreateEditFindParametersDefaultAnswer());
        defaultAnswerHashMap.put("По имени " + Emoji.SPEECH_BALLOON, new FindNameDefaultAnswer());
        defaultAnswerHashMap.put("Подписаться " + Emoji.CHECK, new SubscriptionDefaultAnswer());
        defaultAnswerHashMap.put("Отписаться " + Emoji.X_MARK, new SubscriptionDefaultAnswer());
        defaultAnswerHashMap.put("Удалить " + Emoji.MINUS, new SubscriptionDefaultAnswer());

        defaultAnswerHashMap.put(Emoji.PLUS, new CreateEditFindParametersDefaultAnswer());
        defaultAnswerHashMap.put(Emoji.PENCIL, new CreateEditFindParametersDefaultAnswer());
        defaultAnswerHashMap.put(Emoji.NIB, new CreateEditFindParametersDefaultAnswer());
        defaultAnswerHashMap.put(Emoji.SPEECH_BALLOON, new FindNameDefaultAnswer());
        defaultAnswerHashMap.put(Emoji.CHECK, new SubscriptionDefaultAnswer());
        defaultAnswerHashMap.put(Emoji.X_MARK, new SubscriptionDefaultAnswer());
        defaultAnswerHashMap.put(Emoji.MINUS, new SubscriptionDefaultAnswer());
    }

    @Override
    public String send(Message message) {
        var progress = UserStateCache.getProgress(message.getFrom());
        var operation = progress.getMessage().getOperation();
        return defaultAnswerHashMap.getOrDefault(operation, new UnknownAnswer()).send(message);
    }
}
