package view.commands;

import view.Message;

/**
 * Команда поиска мероприятий за текущую неделю
 */
public class EventWeekFindCommand extends Command{

    @Override
    public String execute(Message message) {
        return eventController.findForTheCurrentWeek();
    }
}
