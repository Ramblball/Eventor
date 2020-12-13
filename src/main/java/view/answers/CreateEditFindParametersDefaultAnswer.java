package view.answers;

import controller.Keywords;
import org.telegram.telegrambots.meta.api.objects.Message;
import view.Progress;
import view.UserStateCache;

public class CreateEditFindParametersDefaultAnswer extends Answer {

    @Override
    public String send(Message message) {
        var user = message.getFrom();
        var received = message.getText();
        var entities = message.getEntities();
        if (UserStateCache.getProgress(user).getIndex() == 0) {
            telegramMessage.setEventName(applyFormatting(received, entities));
            UserStateCache.setProgress(user, new Progress(telegramMessage, 1));
            return "Введите время мероприятия в формате " + Keywords.DATE_TIME_FORMAT;
        }
        if (UserStateCache.getProgress(user).getIndex() == 1) {
            telegramMessage.setEventTime(received);
            UserStateCache.setProgress(user, new Progress(telegramMessage, 2));
            return "Введите место мероприятия";
        }
        if (UserStateCache.getProgress(user).getIndex() == 2) {
            telegramMessage.setEventPlace(applyFormatting(received, entities));
            UserStateCache.setProgress(user, new Progress(telegramMessage, 3));
            return "Введите описание мероприятия";
        }
        if (UserStateCache.getProgress(user).getIndex() == 3) {
            telegramMessage.setEventDescription(applyFormatting(received, entities));
            UserStateCache.setProgress(user, null);
            telegramKeyboard.createOperationMenu();
            return commandMap.getCommandMap().get(telegramMessage.getOperation()).execute(telegramMessage);
        }
        return "-1";
    }
}
