package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс, описывающий поведение ответа
 */
public interface IAnswer {
    String send(Message message);
}
