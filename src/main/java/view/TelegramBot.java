package view;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {
    private final static String botName = "eventor_oop_bot";

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
        return botName;
    }

    @Override
    public String getBotToken() {
        String token = "";
        try {
            var br = new BufferedReader(new FileReader("token.txt"));
            token = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String address = "@" +
                    botName +
                    " ";
            var telegramProvider = new TelegramProvider(update.getMessage().getText().replace(address, ""));
            var answer = telegramProvider.execute();
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage()
                            .getChatId()).setText(answer);
            message.setReplyMarkup(setKeyboard());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
