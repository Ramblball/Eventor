package database.exception;

/**
 * Класс ошибки, выкидываемой при ошибке обращения к базе данных
 */
public class DBException extends Exception{
    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
