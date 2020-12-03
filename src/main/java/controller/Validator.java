package controller;

import controller.exception.LongFieldException;
import controller.exception.ParseException;
import controller.exception.PastTimeException;
import controller.exception.ValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Класс с методами для валидации пользовательского ввода
 */
public class Validator {

    /**
     * Метод для валидации параметров мероприятия
     * @param name                  Название мероприятия
     * @param time                  Время начала мероприятия
     * @param place                 Место проведения мероприятия
     * @param description           Описание мероприятия
     * @throws LongFieldException   Значения полей слишком длинные
     * @throws ParseException       Неверный формат даты
     * @throws PastTimeException    Введенная дата прошла
     */
    public void checkEventParams(String name, String time, String place, String description) throws ValidationException {
        if (name.length() > 32) {
            throw new LongFieldException(Keywords.LONG_NAME);
        }
        if (place.length() > 128) {
            throw new LongFieldException(Keywords.LONG_PLACE);
        }
        if (description.length() > 512) {
            throw new LongFieldException(Keywords.LONG_DESCRIPTION);
        }
        try {
            if (LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.DATE_TIME_FORMAT))
                    .isBefore(LocalDateTime.now())) {
                throw new PastTimeException(Keywords.PAST_DATE);
            }
        } catch (DateTimeParseException e) {
            throw new ParseException(Keywords.INVALID_TIME, e);
        }
    }
}
