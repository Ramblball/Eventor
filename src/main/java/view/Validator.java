package view;

import controller.Keywords;
import view.exception.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Класс с методами для валидации пользовательского ввода
 */
public class Validator {

    /**
     * Метод для валидации названия мероприятия
     * @param name                          Название мероприятия
     * @throws LongFieldException           Значения полей слишком длинные
     */
    public void checkName(String name) throws ValidationException {
        if (name.length() > 32) {
            throw new LongFieldException(Keywords.LONG_NAME);
        }
    }

    /**
     * Метод для валидации времени проведения мероприятия
     * @param time                          Время начала мероприятия
     * @throws ParseException               Неверный формат даты
     * @throws PastTimeException            Введенная дата прошла
     */
    public void checkTime(String time) throws ValidationException {
        try {
            if (LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.DATE_TIME_FORMAT))
                    .isBefore(LocalDateTime.now())) {
                throw new PastTimeException(Keywords.PAST_DATE);
            }
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Метод для валидации ограничения на число участников
     * @param limit                         Ограничение кол-ва участников мероприятия
     * @throws OutOfFieldBorderException    Введенный параметр находится вне допустимых границ
     */
    public void checkLimit(String limit) throws ValidationException {
        try {
            int lim = Integer.parseInt(limit);
            if (lim < 2) {
                throw new OutOfFieldBorderException("Слишком мало мест на мероприятие");
            }
            if (lim > 20) {
                throw new OutOfFieldBorderException("Слишком много мест на мероприятие");
            }
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Метод для валидации описания мероприятия
     * @param description                   Описание мероприятия
     * @throws LongFieldException           Значения полей слишком длинные
     */
    public void checkDescription(String description) throws ValidationException {
        if (description.length() > 512) {
            throw new LongFieldException(Keywords.LONG_DESCRIPTION);
        }
    }
}
