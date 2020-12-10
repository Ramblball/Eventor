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
        firstRow.add("Назад");
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
        firstRow.add("По имени");
        firstRow.add("По параметрам");
        secondRow.add("Назад");
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    /**
     * Создание кнопок основных действий
     */
    public void createOperationMenu() {
        clearKeyboardRows();
        firstRow.add("Создать");
        firstRow.add("Изменить");
        firstRow.add("Удалить");
        thirdRow.add("Мои подписки");
        secondRow.add("Подписаться");
        secondRow.add("Отписаться");
        thirdRow.add("Созданные мероприятия");
        fourthRow.add("Назад");
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
        firstRow.add("Помощь");
        secondRow.add("Управление подписками");
        secondRow.add("Поиск");
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
