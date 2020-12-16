package view.answers;

import controller.Keywords;
import org.telegram.telegrambots.meta.api.objects.Message;
import view.UserStateCache;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.User;
import view.Progress;
import view.TelegramBot;

import java.util.List;

/**
 * Класс, описывающий диалог работы со всеми полями мероприятий
 */
public class CreateEditFindParametersDefaultAnswer extends Answer {

    @Override
    public String send(Message message) {
        User user = message.getFrom();
        String received = message.getText();
        List<MessageEntity> entities = message.getEntities();
        Progress progress = UserStateCache.getProgress(user);
        telegramMessage = progress.getMessage();
        switch (progress.getIndex()) {
            case 0:
                telegramMessage.setEventName(received);
                UserStateCache.setProgress(user, new Progress(telegramMessage, 1));
                return "Введите время мероприятия в формате " + Keywords.DATE_TIME_FORMAT;
            case 1:
                telegramMessage.setEventTime(received);
                UserStateCache.setProgress(user, new Progress(telegramMessage, 2));
                return "Введите место мероприятия";
            case 2:
                telegramMessage.setEventPlace(
                        entities != null
                                ? applyFormatting(received, entities)
                                : received);
                UserStateCache.setProgress(user, new Progress(telegramMessage, 3));
                return "Введите описание мероприятия";
            case 3:
                telegramMessage.setEventDescription(
                        entities != null
                                ? applyFormatting(received, entities)
                                : received);
                UserStateCache.setProgress(user, null);
                telegramKeyboard.createOperationMenu();
                TelegramBot.replyKeyboardMarkup = telegramKeyboard.getReplyKeyboardMarkup();
                return commandMap
                        .getCommandMap()
                        .get(telegramMessage.getOperation())
                        .execute(telegramMessage);
            default:
                return Keywords.EXCEPTION;
        }
    }
}
