package view;

import controller.EventController;
import controller.UserController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Chat extends TelegramLongPollingBot {
    private final UserController userController = new UserController();
    private final EventController eventController = new EventController();
    private final static String fewArgumentsOutput = "Too few arguments. Try to type \"help\"";

    public String execute(String message) {
        var input = message.split(" ");
        switch (input[0]) {
            case "create":
                if (input[1].equals("event")) {
                    if (input.length < 5)
                        return fewArgumentsOutput;
                    return eventController.create(input[2], input[3], input[4]);
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
                return eventController.findEvent(input[1]);
            case "signup":
                if (input.length < 2)
                    return fewArgumentsOutput;
                return userController.signUp(input[1]);
            case "exit":
                return "¯\\_(ツ)_/¯";
            default:
                return "Unknown command. Try to type \"help\"";
        }
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
            var text = this.execute(update.getMessage().getText());
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage()
                            .getChatId()).setText(text);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}