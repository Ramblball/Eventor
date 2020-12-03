package controller;

import controller.exception.LongFieldException;
import controller.exception.ParseException;
import controller.exception.PastTimeException;
import controller.exception.ValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validator {

    /**
     * Валидирует параметры мероприятия
     * @param name                  Название мероприятия
     * @param time                  Время начала мероприятия
     * @param place                 Место проведения мероприятия
     * @param description           Описание мероприятия
     * @throws LongFieldException   Значения полей слишком длинные
     * @throws ParseException       Неверный формат даты
     * @throws PastTimeException    Введенная дата прошла
     */
    public void checkEventsParams(String name, String time, String place, String description) throws ValidationException {
        if (name.length() > 32) {
            throw new LongFieldException(Keywords.longName);
        }
        if (place.length() > 128) {
            throw new LongFieldException(Keywords.longPlace);
        }
        if (description.length() > 512) {
            throw new LongFieldException(Keywords.longDesc);
        }
        try {
            if (LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.dateTimeFormat))
                    .isBefore(LocalDateTime.now())) {
                throw new PastTimeException(Keywords.prevDate);
            }
        } catch (DateTimeParseException e) {
            throw new ParseException(Keywords.invalidTime, e);
        }
    }


}
