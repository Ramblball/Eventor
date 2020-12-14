package view.commands;

import view.TelegramMessage;

/**
 * Команда поиска мероприятий за текущую неделю
 */
public class EventWeekFindCommand extends Command {

    @Override
    public String execute(TelegramMessage telegramMessage) {
        return eventController.findForTheCurrentWeek();
    }
}
