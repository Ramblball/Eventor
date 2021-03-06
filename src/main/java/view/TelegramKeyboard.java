package view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

/**
 * Класс с методами создания клавиатур
 */
public class TelegramKeyboard {
    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private final ArrayList<KeyboardRow> keyboard = new ArrayList<>();
    private final KeyboardRow firstRow = new KeyboardRow();
    private final KeyboardRow secondRow = new KeyboardRow();
    private final KeyboardRow thirdRow = new KeyboardRow();
    private final KeyboardRow fourthRow = new KeyboardRow();

    /**
     * Задание параметров для клавиатуры
     */
    public TelegramKeyboard() {
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
    }

    /**
     * Метод для создания меню, и установки кнопки возврата
     */
    public void hideMenu() {
        clearKeyboardRows();
        firstRow.add("Назад " + Emoji.BACK);
        keyboard.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    /**
     * Метод для отчистки клавиатуры
     */
    public void clearKeyboardRows() {
        keyboard.clear();
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();
        fourthRow.clear();
    }

    /**
     * Метод для создания кнопок поиска
     */
    public void createFindMenu() {
        clearKeyboardRows();
        firstRow.add("По имени " + Emoji.SPEECH_BALLOON);
        firstRow.add("По параметрам " + Emoji.NIB);
        secondRow.add("На текущей неделе " + Emoji.CALENDAR);
        thirdRow.add("Случайное " + Emoji.RANDOM);
        fourthRow.add("Назад " + Emoji.BACK);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        keyboard.add(thirdRow);
        keyboard.add(fourthRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    /**
     * Метод для создания кнопок основных действий
     */
    public void createOperationMenu() {
        clearKeyboardRows();
        firstRow.add("Создать " + Emoji.PLUS);
        firstRow.add("Изменить " + Emoji.PENCIL);
        firstRow.add("Удалить " + Emoji.MINUS);
        thirdRow.add("Мои подписки " + Emoji.BOOKS);
        secondRow.add("Подписаться " + Emoji.CHECK);
        secondRow.add("Отписаться " + Emoji.X_MARK);
        thirdRow.add("Созданные мероприятия " + Emoji.MEMO);
        fourthRow.add("Назад " + Emoji.BACK);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        keyboard.add(thirdRow);
        keyboard.add(fourthRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    /**
     * Метод для создания кнопок с разделами
     */
    public void createMainMenu() {
        clearKeyboardRows();
        firstRow.add("Помощь " + Emoji.INFO);
        secondRow.add("Найти GIF " + Emoji.CAMERA);
        thirdRow.add("Управление подписками " + Emoji.WRENCH);
        thirdRow.add("Поиск " + Emoji.MAGNIFYING_GLASS);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        keyboard.add(thirdRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return replyKeyboardMarkup;
    }
}
