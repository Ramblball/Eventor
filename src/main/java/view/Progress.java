package view;

/**
 * Класс для установки прогресса выполнения текущей команды
 */
public class Progress {
    private final TelegramMessage telegramMessage;
    // Стадия выполнения запроса
    private final int index;

    public Progress(TelegramMessage telegramMessage, int count) {
        this.telegramMessage = telegramMessage;
        this.index = count;
    }

    public TelegramMessage getMessage() {
        return telegramMessage;
    }

    public int getIndex() {
        return index;
    }
}
