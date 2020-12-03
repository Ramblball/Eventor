package controller.exception;

public class PastTimeException extends ValidationException {
    public PastTimeException(String message) {
        super(message);
    }

    public PastTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
