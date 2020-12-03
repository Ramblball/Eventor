package controller.exception;

/**
 * Класс ошибки, выкидываемой при валидации
 */
public abstract class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
