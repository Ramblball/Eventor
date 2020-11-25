package view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public final class Buttons {
    public final static InlineKeyboardButton createUser = new InlineKeyboardButton().setText("Create user").setSwitchInlineQueryCurrentChat("create user ");
    public final static InlineKeyboardButton createEvent = new InlineKeyboardButton().setText("Create event").setSwitchInlineQueryCurrentChat("create event ");
    public final static InlineKeyboardButton getHelp = new InlineKeyboardButton().setText("Get help").setSwitchInlineQueryCurrentChat("help");
    public final static InlineKeyboardButton signUp = new InlineKeyboardButton().setText("Sign up for an event").setSwitchInlineQueryCurrentChat("signup ");
    public final static InlineKeyboardButton logIn = new InlineKeyboardButton().setText("Log in").setSwitchInlineQueryCurrentChat("login ");
    public final static InlineKeyboardButton find = new InlineKeyboardButton().setText("Find").setSwitchInlineQueryCurrentChat("find ");
}
