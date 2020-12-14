package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.UserStateCache;

/**
 * Класс, отвечающий на подписку, отписку, удаление
 */
public class SubscriptionDefaultAnswer extends Answer{
    @Override
    public String send(Message message) {
        var received = message.getText();
        var entities = message.getEntities();
        telegramMessage = UserStateCache.getProgress(message.getFrom()).getMessage();
        telegramKeyboard.createOperationMenu();
        if(entities != null)
            telegramMessage.setEventName(applyFormatting(received, entities));
        else
            telegramMessage.setEventName(received);
        return commandMap.getCommandMap().get(telegramMessage.getOperation()).execute(telegramMessage);
    }
}
