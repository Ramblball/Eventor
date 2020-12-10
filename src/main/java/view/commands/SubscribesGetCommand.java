package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда для получения мероприятий, на которые пользователь подписан
 */
public class SubscribesGetCommand extends Command{

    @Override
    public String execute(Message message) {
        return eventController.getSubscribes(message.getUser().getId());
    }
}
