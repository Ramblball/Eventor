package view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * Класс с описание кнопок взаимодейстия с ботом
 */
public final class Buttons {
    public final static InlineKeyboardButton createEvent = new InlineKeyboardButton().setText("Create event").
            setSwitchInlineQueryCurrentChat("create ");//Кнопка создания мероприятия
    public final static InlineKeyboardButton getHelp = new InlineKeyboardButton().setText("Get help").
            setSwitchInlineQueryCurrentChat("help");//Кнопка получения справки
    public final static InlineKeyboardButton getOwn = new InlineKeyboardButton().setText("Get own events").
            setSwitchInlineQueryCurrentChat("own");//Кнопка получения личных мероприятий
    public final static InlineKeyboardButton getSubs = new InlineKeyboardButton().setText("Get subscribes").
            setSwitchInlineQueryCurrentChat("subs");//Кнопка получения личных мероприятий
    public final static InlineKeyboardButton signUp = new InlineKeyboardButton().setText("Subscribe").
            setSwitchInlineQueryCurrentChat("sub ");//Кнопка подписки на мероприятие
    public final static InlineKeyboardButton find = new InlineKeyboardButton().setText("Find event by name").
            setSwitchInlineQueryCurrentChat("find ");//Кнопка поиска мероприятия по имени
    public final static InlineKeyboardButton signOut = new InlineKeyboardButton().setText("Unsubscribe").
            setSwitchInlineQueryCurrentChat("unsub ");//Кнопка отписки от мероприятия
    public final static InlineKeyboardButton findParams = new InlineKeyboardButton().setText("Find events by parameters").
            setSwitchInlineQueryCurrentChat("findby ");//Кнопка поиска мероприятий от параметров
    public final static InlineKeyboardButton remove = new InlineKeyboardButton().setText("Remove event by id").
            setSwitchInlineQueryCurrentChat("remove ");//Кнопка удаления мероприятия по идентификатору
    public final static InlineKeyboardButton update = new InlineKeyboardButton().setText("Update event").
            setSwitchInlineQueryCurrentChat("update ");//Кнопка редактирования мероприятия
}
