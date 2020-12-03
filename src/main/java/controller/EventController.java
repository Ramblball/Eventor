package controller;

import controller.exception.NotAuthorizedException;
import controller.exception.ValidationException;
import database.DBException;
import database.model.*;
import database.utils.EventQuery;

import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;

/**
 * Обеспечение взаимодействия пользователя с мероприятиями
 */
public class EventController extends Controller {

    /**
     * Возвращает подсказку по оформлению команд для пользователя
     * @return              Помощь
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
     * Создает новое мероприятие
     * @param id            Id создателя
     * @param name          Название мероприятия
     * @param time          Время начала мероприятия
     * @param place         Место проведения мероприятия
     * @param description   Описание мероприятия
     * @return              Результат создания
     */
    public String create(Integer id, String name, String time, String place, String description) {
        try {
            validator.checkEventsParams(name, time, place, description);
            LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.dateTimeFormat));
            User currentUser = getCurrent(id);
            Event event = new Event(name, place, dateTime, Category.Прогулка, description);
            eventService.create(currentUser, event);
            return String.format(Keywords.eventCreated, name);
        } catch (NotAuthorizedException e) {
            return e.getMessage();
        } catch (ValidationException e) {
            return e.getMessage();
        } catch (DBException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Обновляет существующее мероприятие
     * @param id            Id создателя
     * @param name          Название мероприятия
     * @param time          Время начала мероприятия
     * @param place         Место проведения мероприятия
     * @param description   Описание мероприятия
     * @return              Результат обновления
     */
    public String update(Integer id, String name, String time, String place, String description) {
        try {
            validator.checkEventsParams(name, time, place, description);
            User currentUser = getCurrent(id);
            Event event = eventService.findByName(name);
            if (event == null) {
                return Keywords.eventNotFound;
            }
            if (event.getUserId() != currentUser.getId()) {
                return Keywords.notOwnUpdate;
            }
            event.setName(name);
            event.setTime(LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.dateTimeFormat)));
            event.setPlace(place);
            event.setDescription(description);
            eventService.update(event);
            return String.format(Keywords.eventUpdated, name);
        } catch (ValidationException e) {
            return e.getMessage();
        } catch (NotAuthorizedException e) {
            return e.getMessage();
        } catch (DBException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Удаление мероприятия
     * @param userId        Id создателя
     * @param name          Id удаляемого мероприятия
     * @return              Результат удаления
     */
    public String remove(Integer userId, String name) {
        try {
            User currentUser = getCurrent(userId);
            Event event = eventService.findByName(name);
            if (event.getUserId() != currentUser.getId()) {
                return Keywords.notOwnUpdate;
            }
            eventService.remove(currentUser, event);
            return String.format(Keywords.eventRemoved, event.getName());
        } catch (NotAuthorizedException e) {
            return e.getMessage();
        } catch (DBException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Возвращает мероприятия созданные пользователем
     * @param id            Id создателя
     * @return              Список мероприятий
     */
    public String getOwn(Integer id) {
        try {
            User currentUser = getCurrent(id);
            Set<Event> events = currentUser.getCreatedEvents();
            return eventsToString(events);
        } catch (NotAuthorizedException e) {
            return e.getMessage();
        }catch (DBException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Возвращает мероприятия на которые пользователь подписан
     * @param id            Id пользователя
     * @return              Список мероприятий
     */
    public String getSubs(Integer id) {
        try {
            User currentUser = getCurrent(id);
            Set<Event> events = currentUser.getSubscribes();
            return eventsToString(events);
        } catch (NotAuthorizedException e) {
            return e.getMessage();
        }catch (DBException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Возвращает список мероприятий по заданным критериям
     * @param query       EventQuery с заданными параметрами поиска
     * @return            Список мероприятий
     */
    public String find(EventQuery query) {
        try {
            List<Event> events = eventService.find(query);
            return eventsToString(events);
        }catch (DBException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Находит и возвращает мероприятие по его названию
     * @param name        название мероприятия
     * @return            найденное мероприятие
     */
    public String findByName(String name) {
        try {
            var event = eventService.findByName(name);
            return event.toString();
        }catch (DBException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Метод для подписки или отписки от мероприятия
     * @param id            Id создателя
     * @param name          Название мероприятия
     * @param f             Подписаться - true
     *                      Отписаться - false
     * @return              Результат выполнения
     */
    public String subscribeManager(Integer id, String name, boolean f)
            throws NotAuthorizedException, DBException {
        try {
            Event event = eventService.findByName(name);
            User currentUser = getCurrent(id);
            boolean result;
            if (f) {
                eventService.subscribe(currentUser, event);
            } else {
                eventService.unsubscribe(currentUser, event);
            }
        } catch (NumberFormatException e) {
            return Keywords.idNotNumb;
        }
        return null;
    }

    /**
     * Подписывает пользователя на мероприятие
     * @param id            Id создателя
     * @param name          Название мероприятия
     * @return              Результат подписки
     */
    public String signIn(Integer id, String name) {
        try {
            String result = subscribeManager(id, name, true);
            return Keywords.eventSigned;
        } catch (NotAuthorizedException e) {
            return e.getMessage();
        }catch (DBException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Отписывает пользователя от мероприятия
     * @param id            Id создателя
     * @param name          Название мероприятия
     * @return            Результат отписки
     */
    public String signOut(Integer id, String name) {
        try {
            String result = subscribeManager(id, name, false);
            return Keywords.eventUnsigned;
        } catch (NotAuthorizedException e) {
            return e.getMessage();
        }catch (DBException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
    }
    }

    /**
     * Преобразует список пользователей в удобный для чтения формат
     * @param events      Список мероприятий
     * @return            Список мероприятий в удобном для чтения формате
     */
    private String eventsToString(Collection<Event> events) {
        if (events == null) {
            return Keywords.exception;
        }
        if (events.size() == 0) {
            return Keywords.noEvents;
        }
        StringJoiner joiner = new StringJoiner("\n\n");
        for (Event event : events) {
            joiner.add(event.toString());
        }
        return joiner.toString();
    }
}