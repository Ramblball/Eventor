package controller.exception;

/**
 * Класс ошибки, выкидываемой при валидации
 * Длина, введенной пользователем, строки превышает лимит
 */
public class LongFieldException extends ValidationException {
    public LongFieldException(String message) {
        super(message);
    }

    public LongFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
