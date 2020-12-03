package controller.exception;

/**
 * Класс ошибки, выкидываемой при валидации
 * Ошибка при парсинге введенных данных
 */
public class ParseException extends ValidationException {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
