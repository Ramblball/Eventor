package controller.exception;

/**
 * Класс ошибки, выкидываемой при валидации
 * Время введенное пользователем уже прошло на момент создания мероприятия
 */
public class PastTimeException extends ValidationException {
    public PastTimeException(String message) {
        super(message);
    }

    public PastTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
