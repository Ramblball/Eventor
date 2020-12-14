package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import view.UserStateCache;
import java.util.List;

/**
 * Класс для диалога управления подпиской
 */
public class SubscriptionDefaultAnswer extends Answer {

    @Override
    public String send(Message message) {
        String received = message.getText();
        List<MessageEntity> entities = message.getEntities();
        telegramMessage = UserStateCache.getProgress(message.getFrom()).getMessage();
        telegramKeyboard.createOperationMenu();
        if (entities != null) {
            telegramMessage.setEventName(applyFormatting(received, entities));
        } else {
            telegramMessage.setEventName(received);
        }
        return commandMap
                .getCommandMap()
                .get(telegramMessage.getOperation())
                .execute(telegramMessage);
    }
}
