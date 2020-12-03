package controller.exception;

public class LongFieldException extends ValidationException {
    public LongFieldException(String message) {
        super(message);
    }

    public LongFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
