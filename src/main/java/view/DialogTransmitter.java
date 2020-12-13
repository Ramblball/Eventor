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
        answerHashMap.put("Создать", new OperationAnswer());
        answerHashMap.put("Изменить", new OperationAnswer());
        answerHashMap.put("Удалить", new OperationAnswer());
        answerHashMap.put("Подписаться", new OperationAnswer());
        answerHashMap.put("Отписаться", new OperationAnswer());
        answerHashMap.put("По имени", new FindAnswer());
        answerHashMap.put("По параметрам", new FindAnswer());
        answerHashMap.put("На текущей неделе ", new FindWeekAnswer());

        answerHashMap.put("Мои подписки " + Emoji.INFO, new MainMenuAnswer());
        answerHashMap.put("Созданные мероприятия " + Emoji.MEMO, new MainMenuAnswer());
        answerHashMap.put("Помощь " + Emoji.INFO, new MainMenuAnswer());
        answerHashMap.put("Назад " + Emoji.BACK, new ReturnBackAnswer());
        answerHashMap.put("Управление подписками " + Emoji.WRENCH, new OperationMenuAnswer());
        answerHashMap.put("Поиск " + Emoji.MAGNIFYING_GLASS, new FindMenuAnswer());
        answerHashMap.put("Создать " + Emoji.PLUS, new OperationAnswer());
        answerHashMap.put("Изменить " + Emoji.PENCIL, new OperationAnswer());
        answerHashMap.put("Удалить " + Emoji.MINUS, new OperationAnswer());
        answerHashMap.put("Подписаться " + Emoji.CHECK, new OperationAnswer());
        answerHashMap.put("Отписаться " + Emoji.X_MARK, new OperationAnswer());
        answerHashMap.put("По имени " + Emoji.SPEECH_BALLOON, new FindAnswer());
        answerHashMap.put("По параметрам " + Emoji.NIB, new FindAnswer());
        answerHashMap.put("На текущей неделе " + Emoji.CALENDAR, new FindWeekAnswer());
    }

    /**Скрывает, открывает диалоги, сохраняет прогресс диалога
     * @return ответ пользователю
     */
    public String getMessage(Message message) {
        return answerHashMap.get(message.getText()).send(message);
    }
}
