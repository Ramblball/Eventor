package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда для получения мероприятий, на которые пользователь подписан
 */
public class GetSubscribes extends Command{
    EventController eventController = new EventController();

    @Override
    public String execute(Message message) {
        return eventController.getSubs(message.getUser().getUserName());
    }
}
