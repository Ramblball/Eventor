package view.answers;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс, описывающий поведение ответа
 */
public interface IAnswer {

    /**
     * Матод для постоения диалога с пользователем
     * @param message       Объект с введенными данными
     * @return              Ответ на запрос
     */
    String send(Message message);
}
