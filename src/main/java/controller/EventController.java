package controller;

import controller.exception.NotAuthorizedException;
import controller.exception.ValidationException;
import database.exception.DBException;
import database.exception.NotFoundException;
import database.model.*;
import database.utils.EventQuery;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;

/**
 * Обеспечение взаимодействия пользователя с мероприятиями
 */
public class EventController extends Controller {
    private static final Logger logger = LogManager.getLogger(EventController.class);

    /**
     * Метод дял получения подсказки по работе с приложением
     * @return              Помощь
     */
    public String getHelp() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("Это бот для создания мероприятий.\n");
        stringJoiner.add("Управление подписками:");
        stringJoiner.add("  Действия для управления мероприятиями\n");
        stringJoiner.add("Поиск:");
        stringJoiner.add("  По имени - по полному названию мероприятия");
        stringJoiner.add("  По параметрам:");
        stringJoiner.add("      Часть названия мероприятия");
        stringJoiner.add("      Часть места проведения мероприятия");
        stringJoiner.add("      День проведения мероприятия");
        stringJoiner.add("      фрагмент описания мероприятия");
        return stringJoiner.toString();
    }

    /**
     * Метод для создания нового мероприятия
     * @param id            Уникальный идентификатор создателя
     * @param name          Название мероприятия
     * @param time          Время начала мероприятия
     * @param place         Место проведения мероприятия
     * @param description   Описание мероприятия
     * @return              Результат создания
     */
    public String create(Integer id, String name, String time, String place, String description) {
        try {
            validator.checkEventParams(name, time, place, description);
            LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.DATE_TIME_FORMAT));
            User currentUser = getCurrentUser(id);
            Event event = new Event(name, place, dateTime, Category.Прогулка, description);
            eventService.create(currentUser, event);
            return String.format(Keywords.EVENT_CREATED, name);
        } catch (ValidationException e) {
            logger.error(e.getMessage(), e);
            return Keywords.VALIDATION_EXCEPTION + e.getMessage();
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION;
        } catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_CREATE_EXCEPTION;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для обновления сущствующего мероприятия
     * @param id            Уникальный идентификатор создателя
     * @param name          Название мероприятия
     * @param time          Время начала мероприятия
     * @param place         Место проведения мероприятия
     * @param description   Описание мероприятия
     * @return              Результат обновления
     */
    public String update(Integer id, String name, String time, String place, String description) {
        try {
            validator.checkEventParams(name, time, place, description);
            User currentUser = getCurrentUser(id);
            Event event = eventService.findByName(name);
            if (event == null) {
                return Keywords.EVENT_NOT_FOUND;
            }
            if (event.getUserId() != currentUser.getId()) {
                return Keywords.NOT_CREATED_UPDATE;
            }
            event.setName(name);
            event.setTime(LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.DATE_TIME_FORMAT)));
            event.setPlace(place);
            event.setDescription(description);
            eventService.update(event);
            return String.format(Keywords.EVENT_UPDATED, name);
        } catch (ValidationException e) {
            logger.error(e.getMessage(), e);
            return Keywords.VALIDATION_EXCEPTION + e.getMessage();
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION;
        } catch (NotFoundException e) {
            return e.getMessage();
        } catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_UPDATE_EXCEPTION;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для удаления мероприятия
     * @param userId        уникальный идентификатор создателя
     * @param name          Название удаляемого мероприятия
     * @return              Результат удаления
     */
    public String remove(Integer userId, String name) {
        try {
            User currentUser = getCurrentUser(userId);
            Event event = eventService.findByName(name);
            if (event.getUserId() != currentUser.getId()) {
                return Keywords.NOT_CREATED_UPDATE;
            }
            eventService.remove(currentUser, event);
            return String.format(Keywords.EVENT_REMOVED, event.getName());
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION;
        } catch (NotFoundException e) {
            return e.getMessage();
        } catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_REMOVE_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для получения мероприятий созданных пользователем
     * @param id            Уникальный идентификатор создателя
     * @return              Список мероприятий
     */
    public String getCreated(Integer id) {
        try {
            User currentUser = getCurrentUser(id);
            Set<Event> events = currentUser.getCreatedEvents();
            return eventsToString(events);
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION;
        }catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_FIND_BY_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для получения мероприятий на которые пользователь подписан
     * @param id            уникальный идентификатор пользователя
     * @return              Список мероприятий
     */
    public String getSubscribes(Integer id) {
        try {
            User currentUser = getCurrentUser(id);
            Set<Event> events = currentUser.getSubscribes();
            return eventsToString(events);
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION;
        }catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_FIND_BY_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Мето для поиска мероприятий по заданным критериям
     * @param query         EventQuery с заданными параметрами поиска
     * @return              Список мероприятий
     */
    public String findWithFilter(EventQuery query) {
        try {
            List<Event> events = eventService.findWithFilter(query);
            return eventsToString(events);
        } catch (NotFoundException e) {
            return e.getMessage();
        }catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_FIND_BY_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для поиска мероприятие по его названию
     * @param name          Название мероприятия
     * @return              Найденное мероприятие
     */
    public String findByName(String name) {
        try {
            var event = eventService.findByName(name);
            return event.toString();
        } catch (NotFoundException e) {
            return e.getMessage();
        }catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_FIND_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для подписки или отписки от мероприятия
     * @param id            Уникальный идентификатор пользователя
     * @param name          Название мероприятия
     * @param f             Подписаться - true
     *                      Отписаться - false
     */
    public void subscribeManager(Integer id, String name, boolean f)
            throws NotFoundException, NotAuthorizedException, DBException {
        Event event = eventService.findByName(name);
        User currentUser = getCurrentUser(id);
        if (f) {
            eventService.subscribe(currentUser, event);
        } else {
            eventService.unsubscribe(currentUser, event);
        }
    }

    /**
     * Метод для подписки пользователя на мероприятие
     * @param id            Уникальный идентификатор пользователя
     * @param name          Название мероприятия
     * @return              Результат подписки
     */
    public String signIn(Integer id, String name) {
        try {
            subscribeManager(id, name, true);
            return Keywords.EVENT_SIGNED;
        } catch (NotFoundException e) {
            return e.getMessage();
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION;
        }catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_SUB_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для отписки пользователя от мероприятия
     * @param id            Id создателя
     * @param name          Название мероприятия
     * @return            Результат отписки
     */
    public String signOut(Integer id, String name) {
        try {
            subscribeManager(id, name, false);
            return Keywords.EVENT_UNSIGNED;
        } catch (NotFoundException e) {
            return e.getMessage();
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION;
        }catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_UNSUB_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Мето для преобразования списка мероприятий в удобный для чтения формат
     * @param events      Список мероприятий
     * @return            Список мероприятий в удобном для чтения формате
     */
    private String eventsToString(Collection<Event> events) {
        if (events == null) {
            return Keywords.EXCEPTION;
        }
        if (events.size() == 0) {
            return Keywords.NO_EVENTS;
        }
        StringJoiner joiner = new StringJoiner("\n\n");
        for (Event event : events) {
            joiner.add(event.toString());
        }
        return joiner.toString();
    }
}