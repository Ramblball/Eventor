package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;

public class FindNameDefaultAnswer extends Answer{
    @Override
    public String send(Message message) {
        var received = message.getText();
        var entities = message.getEntities();
        telegramMessage.setEventName(applyFormatting(received, entities));
        telegramKeyboard.createFindMenu();
        return commandMap.getCommandMap().get(telegramMessage.getOperation()).execute(telegramMessage);
    }
}
