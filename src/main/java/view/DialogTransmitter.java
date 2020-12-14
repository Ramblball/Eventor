package view;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.answers.*;

import java.util.HashMap;

/**
 * Класс для предоставления диалогов
 */
public class DialogTransmitter {
    private final HashMap<String, Answer> answerHashMap = new HashMap<>();

    DialogTransmitter() {
        answerHashMap.put("Привет", new MainMenuAnswer());
        answerHashMap.put("/start", new MainMenuAnswer());
        answerHashMap.put("Мои подписки", new MainMenuAnswer());
        answerHashMap.put("Созданные мероприятия", new MainMenuAnswer());
        answerHashMap.put("Помощь", new MainMenuAnswer());
        answerHashMap.put("Назад", new ReturnBackAnswer());
        answerHashMap.put("Управление подписками", new OperationMenuAnswer());
        answerHashMap.put("Поиск", new FindMenuAnswer());
        answerHashMap.put("Создать", new OperationFindAnswer());
        answerHashMap.put("Изменить", new OperationFindAnswer());
        answerHashMap.put("Удалить", new OperationFindAnswer());
        answerHashMap.put("Подписаться", new OperationFindAnswer());
        answerHashMap.put("Отписаться", new OperationFindAnswer());
        answerHashMap.put("По имени", new OperationFindAnswer());
        answerHashMap.put("По параметрам", new OperationFindAnswer());
        answerHashMap.put("На текущей неделе ", new FindWeekAnswer());

        answerHashMap.put("Мои подписки " + Emoji.BOOKS, new MainMenuAnswer());
        answerHashMap.put("Созданные мероприятия " + Emoji.MEMO, new MainMenuAnswer());
        answerHashMap.put("Помощь " + Emoji.INFO, new MainMenuAnswer());
        answerHashMap.put("Назад " + Emoji.BACK, new ReturnBackAnswer());
        answerHashMap.put("Управление подписками " + Emoji.WRENCH, new OperationMenuAnswer());
        answerHashMap.put("Поиск " + Emoji.MAGNIFYING_GLASS, new FindMenuAnswer());
        answerHashMap.put("Создать " + Emoji.PLUS, new OperationFindAnswer());
        answerHashMap.put("Изменить " + Emoji.PENCIL, new OperationFindAnswer());
        answerHashMap.put("Удалить " + Emoji.MINUS, new OperationFindAnswer());
        answerHashMap.put("Подписаться " + Emoji.CHECK, new OperationFindAnswer());
        answerHashMap.put("Отписаться " + Emoji.X_MARK, new OperationFindAnswer());
        answerHashMap.put("По имени " + Emoji.SPEECH_BALLOON, new OperationFindAnswer());
        answerHashMap.put("По параметрам " + Emoji.NIB, new OperationFindAnswer());
        answerHashMap.put("На текущей неделе " + Emoji.CALENDAR, new FindWeekAnswer());

        answerHashMap.put(Emoji.BOOKS, new MainMenuAnswer());
        answerHashMap.put(Emoji.MEMO, new MainMenuAnswer());
        answerHashMap.put(Emoji.INFO, new MainMenuAnswer());
        answerHashMap.put(Emoji.BACK, new ReturnBackAnswer());
        answerHashMap.put(Emoji.WRENCH, new OperationMenuAnswer());
        answerHashMap.put(Emoji.MAGNIFYING_GLASS, new FindMenuAnswer());
        answerHashMap.put(Emoji.PLUS, new OperationFindAnswer());
        answerHashMap.put(Emoji.PENCIL, new OperationFindAnswer());
        answerHashMap.put(Emoji.MINUS, new OperationFindAnswer());
        answerHashMap.put(Emoji.CHECK, new OperationFindAnswer());
        answerHashMap.put(Emoji.X_MARK, new OperationFindAnswer());
        answerHashMap.put(Emoji.SPEECH_BALLOON, new OperationFindAnswer());
        answerHashMap.put(Emoji.NIB, new OperationFindAnswer());
        answerHashMap.put(Emoji.CALENDAR, new FindWeekAnswer());
    }

    /**
     * @param message сообщение из клиента телеграма
     * @return ответ на сообщение из телеграма
     */
    public String getMessage(Message message) {
        return answerHashMap.getOrDefault(message.getText(), new DefaultAnswer()).send(message);
    }
}
