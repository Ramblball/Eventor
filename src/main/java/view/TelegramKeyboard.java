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
    TelegramKeyboard(){
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
    }

    /**
     * Скрытие меню, установка кнопки возврата
     */
    public void hideMenu() {
        clearKeyboardRows();
        firstRow.add("Назад " + Emoji.BACK);
        keyboard.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    /**
     * Очистка клавиатуры
     */
    public void clearKeyboardRows() {
        keyboard.clear();
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();
        fourthRow.clear();
    }

    /**
     * Создание кнопок поиска
     */
    public void createFindMenu() {
        clearKeyboardRows();
        firstRow.add("По имени " + Emoji.SPEECH_BALLOON);
        firstRow.add("По параметрам " + Emoji.NIB);
        secondRow.add("На текущей неделе " + Emoji.CALENDAR);
        thirdRow.add("Назад " + Emoji.BACK);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        keyboard.add(thirdRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    /**
     * Создание кнопок основных действий
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
     * Создание кнопок с разделами
     */
    public void createMainMenu() {
        clearKeyboardRows();
        firstRow.add("Помощь " + Emoji.INFO);
        secondRow.add("Управление подписками " + Emoji.WRENCH);
        secondRow.add("Поиск " + Emoji.MAGNIFYING_GLASS);
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    /**
     * @return разметка клавиатуры
     */
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return replyKeyboardMarkup;
    }
}
