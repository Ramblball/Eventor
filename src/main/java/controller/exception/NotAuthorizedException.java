package controller.exception;

/**
 * Класс ошибки, выкидываемой при выполнении пользовательских команд
 * Пользователь не авторизирован в системе
 */
public class NotAuthorizedException extends Exception {
    public NotAuthorizedException(String message) {
        super(message);
    }
}
