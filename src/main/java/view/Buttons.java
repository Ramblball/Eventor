package view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public final class Buttons {
    public final static InlineKeyboardButton createUser = new InlineKeyboardButton().setText("Create user").setSwitchInlineQueryCurrentChat("create user ");
    public final static InlineKeyboardButton createEvent = new InlineKeyboardButton().setText("Create event").setSwitchInlineQueryCurrentChat("create event ");
    public final static InlineKeyboardButton getHelp = new InlineKeyboardButton().setText("Get help").setSwitchInlineQueryCurrentChat("help");
    public final static InlineKeyboardButton signUp = new InlineKeyboardButton().setText("Subscribe").setSwitchInlineQueryCurrentChat("signup ");
    public final static InlineKeyboardButton logIn = new InlineKeyboardButton().setText("Log in").setSwitchInlineQueryCurrentChat("login ");
    public final static InlineKeyboardButton find = new InlineKeyboardButton().setText("Find event by name").setSwitchInlineQueryCurrentChat("find ");
    public final static InlineKeyboardButton logOut = new InlineKeyboardButton().setText("Log out").setSwitchInlineQueryCurrentChat("exit");
    public final static InlineKeyboardButton signOut = new InlineKeyboardButton().setText("Unsubscribe").setSwitchInlineQueryCurrentChat("sign out ");
    public final static InlineKeyboardButton findParams = new InlineKeyboardButton().setText("Find events by parameters").setSwitchInlineQueryCurrentChat("findp ");
    public final static InlineKeyboardButton remove = new InlineKeyboardButton().setText("Remove event by id").setSwitchInlineQueryCurrentChat("remove ");
    public final static InlineKeyboardButton update = new InlineKeyboardButton().setText("Update event").setSwitchInlineQueryCurrentChat("update ");


}
