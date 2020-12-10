package view.commands;

import controller.EventController;
import view.Message;

/**
 * Команда для получения мероприятий созданных пользователем
 */
public class OwnEventsGetCommand extends Command{

    @Override
    public String execute(Message message) {
        return eventController.getCreated(message.getUser().getId());
    }
}
