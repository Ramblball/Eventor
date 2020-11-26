package view;

import controller.EventController;
import controller.UserController;
import database.utils.EventQuery;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class Chat extends TelegramLongPollingBot {
    private final UserController userController = new UserController();
    private final EventController eventController = new EventController();
    private final static String fewArgumentsOutput = "Too few arguments. Try to type \"help\"";

    public String execute(String message) {
        var input = message.split(" ");
        switch (input[0]) {
            case "create":
                if (input[1].equals("event")) {
                    if (input.length < 7)
                        return fewArgumentsOutput;
                    return eventController.create(input[2], input[3] + " " + input[4], input[5], input[6]);
                } else {
                    if (input.length < 4)
                        return fewArgumentsOutput;
                    return userController.create(input[2], input[3]);
                }
            case "login":
                if (input.length < 3)
                    return fewArgumentsOutput;
                return userController.logIn(input[1], input[2]);
            case "help":
                return eventController.getHelp();
            case "find":
                if (input.length < 2)
                    return fewArgumentsOutput;
                return eventController.findByName(input[1]);
            case "findp":
                if (input.length < 3)
                    return fewArgumentsOutput;
                var eventQuery = new EventQuery();
                var params = new String[5];
                if (input.length < 7)
                    System.arraycopy(input, 2, params, 0, input.length - 2);
                eventQuery.setName(params[0]);
                eventQuery.setPlace(params[1]);
                eventQuery.setTime(params[2]);
                eventQuery.setDescription(params[3]);
                eventQuery.setCategory(params[4]);
                return eventController.find(eventQuery);
            case "update":
                if (input.length < 7)
                    return fewArgumentsOutput;
                return eventController.update(input[2], input[3], input[4] + " " + input[5], input[6], input[7]);
            case "signup":
                if (input.length < 2)
                    return fewArgumentsOutput;
                return userController.signIn(input[1]);
            case "unsubscribe":
                if (input.length < 2)
                    return fewArgumentsOutput;
                return userController.signOut(input[1]);
            case "remove":
                if (input.length < 2)
                    return fewArgumentsOutput;
                return eventController.remove(input[1]);
            case "exit":
                return userController.logOut();
            default:
                return "Unknown command. Try to type \"help\"";
        }
    }

    private InlineKeyboardMarkup setKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        List<InlineKeyboardButton> fifthRow = new ArrayList<>();
        List<InlineKeyboardButton> sixthRow = new ArrayList<>();


        firstRow.add(Buttons.getHelp);
        secondRow.add(Buttons.createUser);
        secondRow.add(Buttons.logIn);
        thirdRow.add(Buttons.createEvent);
        thirdRow.add(Buttons.signUp);
        thirdRow.add(Buttons.signOut);
        fourthRow.add(Buttons.find);
        fourthRow.add(Buttons.findParams);
        fifthRow.add(Buttons.remove);
        fifthRow.add(Buttons.update);
        sixthRow.add(Buttons.logOut);

        rowsInline.add(firstRow);
        rowsInline.add(secondRow);
        rowsInline.add(thirdRow);
        rowsInline.add(fourthRow);
        rowsInline.add(fifthRow);
        rowsInline.add(sixthRow);

        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

    @Override
    public String getBotUsername() {
        return "eventor_oop_bot";
    }

    @Override
    public String getBotToken() {
        return "1409038970:AAFQxqaP-N0KHxSPBiKqTmUY6jRkixvlIoU";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var text = this.execute(update.getMessage().getText().replace("@eventor_oop_bot ", ""));
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage()
                            .getChatId()).setText(text);
            message.setReplyMarkup(setKeyboard());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}