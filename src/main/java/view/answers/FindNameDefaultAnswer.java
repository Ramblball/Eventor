package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.UserStateCache;

public class FindNameDefaultAnswer extends Answer{
    @Override
    public String send(Message message) {
        var received = message.getText();
        var entities = message.getEntities();
        telegramMessage = UserStateCache.getProgress(message.getFrom()).getMessage();
        if(entities != null)
            telegramMessage.setEventName(applyFormatting(received, entities));
        else
            telegramMessage.setEventName(received);
        telegramKeyboard.createFindMenu();
        return commandMap.getCommandMap().get(telegramMessage.getOperation()).execute(telegramMessage);
    }
}
