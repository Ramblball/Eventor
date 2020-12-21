package controller;

import controller.exception.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Класс с методами для валидации пользовательского ввода
 */
public class Validator {

    /**
     * Метод для валидации параметров мероприятия
     * @param name                          Название мероприятия
     * @param time                          Время начала мероприятия
     * @param limit                         Ограничение кол-ва участников мероприятия
     * @param description                   Описание мероприятия
     * @throws LongFieldException           Значения полей слишком длинные
     * @throws ParseException               Неверный формат даты
     * @throws PastTimeException            Введенная дата прошла
     * @throws OutOfFieldBorderException    Введенный параметр находится вне допустимых границ
     */
    public void checkEventParams(String name, String time, String limit, String description) throws ValidationException {
        if (name.length() > 32) {
            throw new LongFieldException(Keywords.LONG_NAME);
        }
        if (description.length() > 512) {
            throw new LongFieldException(Keywords.LONG_DESCRIPTION);
        }
        try {
            int lim = Integer.parseInt(limit);
            if (lim < 2) {
                throw new OutOfFieldBorderException("Слишком мало мест на мероприятие");
            }
            if (lim > 20) {
                throw new OutOfFieldBorderException("Слишком много мест на мероприятие");
            }
            if (LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.DATE_TIME_FORMAT))
                    .isBefore(LocalDateTime.now())) {
                throw new PastTimeException(Keywords.PAST_DATE);
            }
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }
}
