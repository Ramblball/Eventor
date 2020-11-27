package view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public final class Buttons {
    public final static InlineKeyboardButton createUser = new InlineKeyboardButton().setText("Create user").
            setSwitchInlineQueryCurrentChat("create user ");//Кнопка создания пользователя
    public final static InlineKeyboardButton createEvent = new InlineKeyboardButton().setText("Create event").
            setSwitchInlineQueryCurrentChat("create event ");//Кнопка создания мероприятия
    public final static InlineKeyboardButton getHelp = new InlineKeyboardButton().setText("Get help").
            setSwitchInlineQueryCurrentChat("help");//Кнопка получения справки
    public final static InlineKeyboardButton signUp = new InlineKeyboardButton().setText("Subscribe").
            setSwitchInlineQueryCurrentChat("signup ");//Кнопка подписки на мероприятие
    public final static InlineKeyboardButton logIn = new InlineKeyboardButton().setText("Log in").
            setSwitchInlineQueryCurrentChat("login ");//Кнопка входа пользователя в учётную запись
    public final static InlineKeyboardButton find = new InlineKeyboardButton().setText("Find event by name").
            setSwitchInlineQueryCurrentChat("find ");//Кнопка поиска мероприятия по имени
    public final static InlineKeyboardButton logOut = new InlineKeyboardButton().setText("Log out").
            setSwitchInlineQueryCurrentChat("exit");//Кнопка выхода пользователя из учётной записи
    public final static InlineKeyboardButton signOut = new InlineKeyboardButton().setText("Unsubscribe").
            setSwitchInlineQueryCurrentChat("sign out ");//Кнопка отписки от мероприятия
    public final static InlineKeyboardButton findParams = new InlineKeyboardButton().setText("Find events by parameters").
            setSwitchInlineQueryCurrentChat("findp ");//Кнопка поиска мероприятий от параметров
    public final static InlineKeyboardButton remove = new InlineKeyboardButton().setText("Remove event by id").
            setSwitchInlineQueryCurrentChat("remove ");//Кнопка удаления мероприятия по идентификатору
    public final static InlineKeyboardButton update = new InlineKeyboardButton().setText("Update event").
            setSwitchInlineQueryCurrentChat("update ");//Кнопка редактирования мероприятия
}
