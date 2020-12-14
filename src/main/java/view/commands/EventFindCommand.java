package view.commands;

import view.TelegramMessage;

/**
 * Команда поиска мероприятий по имени
 */
public class EventFindCommand extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return eventController.findByName(telegramMessage.getEventName());
    }
}
