package view.answers;

import controller.Keywords;
import org.telegram.telegrambots.meta.api.objects.Message;
import view.Progress;
import view.TelegramMessage;
import view.UserStateCache;

public class CreateEditFindParametersDefaultAnswer extends Answer {
    //TODO: Если не заработает, то перенеси telegramMessage в кэш
    @Override
    public String send(Message message) {
        var user = message.getFrom();
        var received = message.getText();
        var entities = message.getEntities();
        var progress = UserStateCache.getProgress(user);
        telegramMessage = progress.getMessage();
        if (UserStateCache.getProgress(user).getIndex() == 0) {
            if (entities != null)
                telegramMessage.setEventName(applyFormatting(received, entities));
            else
                telegramMessage.setEventName(received);
            UserStateCache.setProgress(user, progress, 1);
            return "Введите время мероприятия в формате " + Keywords.DATE_TIME_FORMAT;
        }
        if (UserStateCache.getProgress(user).getIndex() == 1) {
            telegramMessage.setEventTime(received);
            UserStateCache.setProgress(user, progress,2);
            return "Введите место мероприятия";
        }
        if (UserStateCache.getProgress(user).getIndex() == 2) {
            if (entities != null)
                telegramMessage.setEventName(applyFormatting(received, entities));
            else
                telegramMessage.setEventName(received);
            UserStateCache.setProgress(user, progress,3);
            return "Введите описание мероприятия";
        }
        if (UserStateCache.getProgress(user).getIndex() == 3) {
            if (entities != null)
                telegramMessage.setEventName(applyFormatting(received, entities));
            else
                telegramMessage.setEventName(received);
            UserStateCache.setProgress(user, null);
            telegramKeyboard.createOperationMenu();
            return commandMap.getCommandMap().get(telegramMessage.getOperation()).execute(telegramMessage);
        }
        return "-1";
    }
}
