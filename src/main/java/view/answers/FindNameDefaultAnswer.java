package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import view.UserStateCache;
import java.util.List;

/**
 * Класс для посторения диалога поиска по имени
 */
public class FindNameDefaultAnswer extends Answer {

    @Override
    public String send(Message message) {
        String received = message.getText();
        List<MessageEntity> entities = message.getEntities();
        telegramMessage = UserStateCache.getProgress(message.getFrom()).getMessage();
        if (entities != null) {
            telegramMessage.setEventName(applyFormatting(received, entities));
        } else {
            telegramMessage.setEventName(received);
        }
        telegramKeyboard.createFindMenu();
        return commandMap
                .getCommandMap()
                .get(telegramMessage.getOperation())
                .execute(telegramMessage);
    }
}
