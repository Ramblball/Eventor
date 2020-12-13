package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.TelegramMessage;
import view.commands.Unknown;

public class UnknownAnswer extends Answer{
    @Override
    public String send(Message message) {
        return new Unknown().execute(new TelegramMessage(message.getFrom()));
    }
}
