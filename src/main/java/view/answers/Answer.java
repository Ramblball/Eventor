package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.User;
import view.CommandMap;
import view.TelegramMessage;
import view.TelegramKeyboard;
import view.UserStateCache;

import java.util.List;

/**
 * Класс, описывающий общую логику диалогов
 */
public abstract class Answer implements IAnswer {
    protected TelegramKeyboard telegramKeyboard = new TelegramKeyboard();
    protected TelegramMessage telegramMessage;
    protected final CommandMap commandMap = new CommandMap();

    public abstract String send(Message message);

    /**
     * Проверяет существование пользователя в кэше
     * @param message   Сообщение из клиента телеграма
     * @return          Обработанное сообщение типа TelegramMessage
     */
    protected TelegramMessage checkProgress(Message message) {
        User user = message.getFrom();
        if (UserStateCache.getProgress(user) != null) {
            telegramMessage = UserStateCache.getProgress(user).getMessage();
        } else {
            telegramMessage = new TelegramMessage(user);
        }
        return telegramMessage;
    }

    /**
     * Применяет форматирование для сохраняемых данных
     * @param text      Текст, который нужно отформатировать
     * @param entities  Сущности, хранящие информацию о форматировании
     * @return          Отформатированный текст
     */
    protected String applyFormatting(String text, List<MessageEntity> entities) {
        if (entities.isEmpty())
            return text;
        StringBuilder sb = new StringBuilder();
        int shift = 0;
        sb.append(text);
        for (MessageEntity entity : entities) {
            switch (entity.getType()) {
                case "bold":
                    sb.insert(entity.getOffset() + shift, "<b>");
                    shift += 3;
                    sb.insert(entity.getLength() + shift, "</b>");
                    shift += 4;
                    break;
                case "italic":
                    sb.insert(entity.getOffset() + shift, "<i>");
                    shift += 3;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</i>");
                    shift += 4;
                    break;
                case "underline":
                    sb.insert(entity.getOffset() + shift, "<u>");
                    shift += 3;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</u>");
                    shift += 4;
                    break;
                case "strikethrough":
                    sb.insert(entity.getOffset() + shift, "<s>");
                    shift += 3;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</s>");
                    shift += 4;
                    break;
                case "code":
                    sb.insert(entity.getOffset() + shift, "<code>");
                    shift += 6;
                    sb.insert(entity.getOffset() + entity.getLength() + shift, "</code>");
                    shift += 7;
                    break;
            }
        }
        return sb.toString();
    }
}
