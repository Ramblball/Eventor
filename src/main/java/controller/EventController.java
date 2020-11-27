package controller;

import database.model.*;
import database.services.EventService;
import database.utils.EventQuery;
import org.hibernate.cfg.NotYetImplementedException;

import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;

/**
 * Класс-слой зваимодействия между пользовательским представлением и внутрееней моделью приложения
 * Обеспечивает взаимодействие пользователя с мероприятиями
 */
public class EventController extends Controller {
    private final EventService eventService = new EventService();
    private StringBuilder stringBuilder = new StringBuilder();
    /**
     * Возвращает подсказку по оформлению команд для пользователя
     * @return            Помощь
     */
    public String getHelp() {
        return Keywords.help;
    }
    /**
     * Валидирует параметры мероприятия
     * @param name        Название мероприятия
     * @param time        Время начала мероприятия
     * @param place       Место проведения мероприятия
     * @param description Описание мероприятия
     * @return            Результат валидации
     */
    public String checkEventsParams(String name, String time, String place, String description) {
        if (isLogout()) {
            return Keywords.unLogin;
        }
        if (name.length() > 32) {
            return Keywords.longName;
        }
        if (place.length() > 128) {
            return Keywords.longPlace;
        }
        if (description.length() > 512) {
            return Keywords.longDesc;
        }
        try {
            LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.dateTimeFormat));
        } catch (DateTimeParseException e) {
            return Keywords.invalidTime;
        }
        return null;
    }
    /**
     * Создает новое мероприятие
     * @param name        Название мероприятия
     * @param time        Время начала мероприятия
     * @param place       Место проведения мероприятия
     * @param description Описание мероприятия
     * @return            Результат создания
     */
    public String create(String user, String name, String time, String place, String description) {
        String checking = checkEventsParams(name, time, place, description);
        if (checking != null) {
            return checking;
        }
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.dateTimeFormat));
        Event event = new Event(name, place, dateTime, Category.Прогулка, description);
        boolean result = eventService.create(currentUser, event);
        if (!result) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.event);
        stringBuilder.append(event.getName());
        stringBuilder.append(Keywords.added);
        return stringBuilder.toString();
    }
    /**
     * Update an existing event
     * @param id          Id обновляемого мероприятия
     * @param name        Название мероприятия
     * @param time        Время начала мероприятия
     * @param place       Место проведения мероприятия
     * @param description Описание мероприятия
     * @return            Результат обновления
     */
    public String update(String user, String id, String name, String time, String place, String description) {
        String checking = checkEventsParams(name, time, place, description);
        if (checking != null) {
            return checking;
        }
        Event event;
        try {
            event = eventService.findById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return Keywords.idNotNumb;
        }
        if (event == null) {
            return Keywords.noEventId;
        }
        if (event.getUserId() != currentUser.getId()) {
            return Keywords.notOwnUpdate;
        }
        event.setName(name);
        event.setPlace(place);
        event.setDescription(description);
        boolean result = eventService.update(event);
        if (!result) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.event);
        stringBuilder.append(event.getName());
        stringBuilder.append(Keywords.updated);
        return stringBuilder.toString();
    }
    /**
     * Удаление мероприятия
     * @param id          Id удаляемого мероприятия
     * @return            Результат удаления
     */
    public String remove(String user, String id) {
        if (isLogout()) {
            return Keywords.unLogin;
        }
        Event event;
        try {
            event = eventService.findById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return Keywords.idNotNumb;
        }
        if (event.getUserId() != currentUser.getId()) {
            return Keywords.notOwnUpdate;
        }
        boolean result = eventService.remove(currentUser, event);
        if (!result) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.event);
        stringBuilder.append(event.getName());
        stringBuilder.append(Keywords.removed);
        return stringBuilder.toString();
    }
    /**
     * Возвращаем мероприятия созданные пользователем
     * @param user        Имя пользователя
     * @return            Список мероприятий
     */
    public String getOwn(String user) {
        throw new NotYetImplementedException();
    }
    /**
     * Возвращает список мероприятий по заданным критериям
     * @param query       EventQuery с заданными параметрами поиска
     * @return            Список мероприятий
     */
    public String find(EventQuery query) {
        if (isLogout()) {
            return Keywords.unLogin;
        }
        List<Event> events = eventService.find(query);
        if (events == null) {
            return Keywords.exception;
        }
        StringJoiner joiner = new StringJoiner("\n\n");
        for (Event event : events) {
            joiner.add(event.toString());
        }
        return joiner.toString();
    }
    /**
     * Находит и возвращает мероприятие по его названию
     * @param name        название мероприятия
     * @return            найденное мероприятие
     */
    public String findByName(String name) {
        var event = eventService.findByName(name);
        if (event == null) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.found);
        stringBuilder.append(Keywords.event);
        stringBuilder.append(event.toString());
        return stringBuilder.toString();
    }
}
