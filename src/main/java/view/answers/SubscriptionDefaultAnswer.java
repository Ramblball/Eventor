package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;

public class SubscriptionDefaultAnswer extends Answer{
    @Override
    public String send(Message message) {
        var received = message.getText();
        var entities = message.getEntities();
        telegramKeyboard.createOperationMenu();
        telegramMessage.setEventName(applyFormatting(received, entities));
        return commandMap.getCommandMap().get(telegramMessage.getOperation()).execute(telegramMessage);
    }
}
