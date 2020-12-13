package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;
import view.TelegramKeyboard;

public interface IAnswer {
    public String send(Message message);
}
