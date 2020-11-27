package controller;

import database.model.*;
import database.utils.EventQuery;
import org.hibernate.cfg.NotYetImplementedException;

import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;

/**
 * Обеспечение взаимодействия пользователя с мероприятиями
 */
public class EventController extends Controller {

    /**
     * Возвращает подсказку по оформлению команд для пользователя
     * @return            Помощь
     */
    public String getHelp() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("Это бот для создания мероприятий.");
        stringJoiner.add("Для создания мероприятия введите:");
        stringJoiner.add("\"create event *name* *time:(yyyy-MM-dd HH:mm)* *place* *description*\"");
        stringJoiner.add("Для обновления мероприятия введите:");
        stringJoiner.add("\"update *name* *time:(yyyy-MM-dd HH:mm)* *place* *description*");
        stringJoiner.add("Для удаления мероприятия введите: \"*event id*\"");
        stringJoiner.add("Для поиска мероприятия по имени введите: \"find *eventName\"");
        stringJoiner.add("Для участия в мероприятии введите: \"signup *event id*\"");
        stringJoiner.add("Для перкращения участия введите: \"unsubscribe *event id*\"");
        stringJoiner.add("Для поиска мероприятия по параметрам (Все опцианальные) введите:");
        stringJoiner.add("\"findp *name* *place* *time:(yyyy-MM-dd HH:mm)* *description* *category*\"");
        return stringJoiner.toString();
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
     * @param user        Имя создателя
     * @param name        Название мероприятия
     * @param time        Время начала мероприятия
     * @param place       Место проведения мероприятия
     * @param description Описание мероприятия
     * @return            Результат создания
     */
    public String create(String user, String name, String time, String place, String description) {
        String validate = checkEventsParams(name, time, place, description);
        if (validate != null) {
            return validate;
        }
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.dateTimeFormat));
        User currentUser = getCurrent(user);
        Event event = new Event(name, place, dateTime, Category.Прогулка, description);
        boolean result = eventService.create(currentUser, event);
        if (!result) {
            return Keywords.exception;
        }
        return String.format(Keywords.eventCreated, name);
    }

    /**
     * Обновляет существующее мероприятие
     * @param user        Имя создателя
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
        User currentUser = getCurrent(user);
        Event event;
        try {
            event = eventService.findById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return Keywords.idNotNumb;
        }
        if (event == null) {
            return Keywords.eventNotFound;
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
        return String.format(Keywords.eventUpdated, name);
    }

    /**
     * Удаление мероприятия
     * @param user        Имя создателя
     * @param id          Id удаляемого мероприятия
     * @return            Результат удаления
     */
    public String remove(String user, String id) {
        User currentUser = getCurrent(user);
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
        return String.format(Keywords.eventRemoved, event.getName());
    }

    /**
     * Возвращаем мероприятия созданные пользователем
     * @param user        Имя создателя
     * @return            Список мероприятий
     */
    public String getOwn(String user) {
        throw new NotYetImplementedException();
    }

    public String getSubs(String user) {
        throw  new NotYetImplementedException();
    }

    /**
     * Возвращает список мероприятий по заданным критериям
     * @param query       EventQuery с заданными параметрами поиска
     * @return            Список мероприятий
     */
    public String find(EventQuery query) {
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
        return event.toString();
    }

    /**
     * Метод для подписки или отписки от мероприятия
     * @param user        Имя создателя
     * @param id          Id мероприятия
     * @param f           Подписаться - true
     *                    Отписаться - false
     * @return            Результат выполнения
     */
    public String subscribeManager(String user, String id, boolean f) {
        try {
            Event event;
            event = eventService.findById(Integer.parseInt(id));
            if (event == null) {
                return Keywords.eventNotFound;
            }
            User currentUser = getCurrent(user);
            boolean result;
            if (f) {
                result = eventService.subscribe(currentUser, event);
            } else {
                result = eventService.unsubscribe(currentUser, event);
            }
            if (!result) {
                return Keywords.exception;
            }
        } catch (NumberFormatException e) {
            return Keywords.idNotNumb;
        }
        return null;
    }

    /**
     * Подписывает пользователя на мероприятие
     * @param user        Имя создателя
     * @param id          Id мероприятия
     * @return            Результат подписки
     */
    public String signIn(String user, String id) {
        String result = subscribeManager(user, id, true);
        if (result == null) {
            return Keywords.eventSigned;
        }
        return result;
    }

    /**
     * Отписывает пользователя от мероприятия
     * @param user        Имя создателя
     * @param id          Id мероприятия
     * @return            Результат отписки
     */
    public String signOut(String user, String id) {
        String result = subscribeManager(user, id, false);
        if (result == null) {
            return Keywords.eventUnsigned;
        }
        return result;
    }
}