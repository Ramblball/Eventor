package view.dialog;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс, описывающий поведение ответа
 */
public interface IDialog {

    /**
     * Метод для построения диалога с пользователем
     * @param message       Объект с введенными данными
     * @return              Ответ на запрос
     */
    String send(Message message);
}
