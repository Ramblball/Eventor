package controller;

import view.exception.NotAuthorizedException;
import database.exception.DBException;
import database.exception.NotFoundException;
import database.model.*;
import database.utils.EventQuery;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.*;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * Класс для взаимодействия пользователя с мероприятиями
 */
public class EventController extends Controller {
    private static final Logger logger = LogManager.getLogger(EventController.class);

    /**
     * Метод для получения подсказки по работе с приложением
     * @return              Помощь
     */
    public String getHelp() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("<b>Это бот для создания мероприятий.</b>\n");
        stringJoiner.add("<i>Управление подписками:</i>");
        stringJoiner.add("  Действия для управления мероприятиями\n");
        stringJoiner.add("<i>Поиск:</i>");
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
     * @param lat           Широта места проведения мероприятия
     * @param lng           Долгота места проведения мероприятия
     * @param limit         Максимальное количество подписчиков
     * @param description   Описание мероприятия
     * @return              Результат создания
     */
    public String create(Integer id, String name, String time,
                         String place, Float lat, Float lng, String limit, String description) {
        try {
            User currentUser = getCurrentUser(id);
            LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(Keywords.DATE_TIME_FORMAT));
            Integer intLimit = Integer.parseInt(limit);
            Event event = new Event(name, place, lat, lng, intLimit, dateTime, currentUser.getId(), description);
            eventService.create(event);
            return String.format(Keywords.EVENT_CREATED, name);
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION + e.getMessage();
        } catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_CREATE_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для обновления существующего мероприятия
     * @param id            Уникальный идентификатор создателя
     * @param name          Название мероприятия
     * @param time          Время начала мероприятия
     * @param place         Место проведения мероприятия
     * @param lat           Широта места проведения мероприятия
     * @param lng           Долгота места проведения мероприятия
     * @param limit         Максимальное количество подписчиков
     * @param description   Описание мероприятия
     * @return              Результат обновления
     */
    public String update(Integer id, String name, String time,
                         String place, Float lat, Float lng, String limit, String description) {
        try {
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
            event.setLatitude(lat);
            event.setLongitude(lng);
            event.setLimit(Integer.parseInt(limit));
            event.setDescription(description);
            eventService.update(event);
            return String.format(Keywords.EVENT_UPDATED, name);
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION + e.getMessage();
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
     * @param userId        Уникальный идентификатор создателя
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
            eventService.remove(event);
            return String.format(Keywords.EVENT_REMOVED, event.getName());
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION + e.getMessage();
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
            return Keywords.AUTH_EXCEPTION + e.getMessage();
        }catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_FIND_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для получения мероприятий на которые пользователь подписан
     * @param id            Уникальный идентификатор пользователя
     * @return              Список мероприятий
     */
    public String getSubscribes(Integer id) {
        try {
            User currentUser = getCurrentUser(id);
            Set<Event> events = currentUser.getSubscribes();
            return eventsToString(events);
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION + e.getMessage();
        }catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_FIND_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для поиска мероприятий по заданным критериям
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
            return Keywords.EVENT_FIND_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для поиска мероприятия по его названию
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
     * Метод для предоставления мероприятий, которые случатся на текущей неделе
     * @return              Найденное мероприятие
     */
    public String findForTheCurrentWeek() {
        try {
            LocalDate now = LocalDate.now();
            TemporalField field = WeekFields.of(Locale.FRANCE).dayOfWeek();
            LocalDateTime begin = now.with(field, 1).atStartOfDay();
            LocalDateTime end = begin.plusDays(7);
            List<Event> events = eventService.findWithInterval(begin, end);
            return eventsToString(events);
        } catch (NotFoundException e) {
            return e.getMessage();
        } catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_FIND_EXCEPTION + e.getMessage();
        }
    }

    /**
     * Метод для поиска случайного мероприятия
     * @return              Случайное мероприятие
     */
    public String findRandom() {
        try {
            Random random = new Random();
            List<Event> events = eventService.findAll();
            int index = random.nextInt(events.size());
            return events.get(index).toString();
        } catch (NotFoundException e) {
            return e.getMessage();
        } catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_FIND_EXCEPTION + e.getMessage();
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
            User currentUser = getCurrentUser(id);
            Event event = eventService.findByName(name);
            eventService.subscribe(currentUser, event);
            return Keywords.EVENT_SIGNED;
        } catch (NotFoundException e) {
            return e.getMessage();
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION + e.getMessage();
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
     * @param id            Уникальный идентификатор создателя
     * @param name          Название мероприятия
     * @return              Результат отписки
     */
    public String signOut(Integer id, String name) {
        try {
            User currentUser = getCurrentUser(id);
            Event event = eventService.findByName(name);
            eventService.unsubscribe(currentUser, event);
            return Keywords.EVENT_UNSIGNED;
        } catch (NotFoundException e) {
            return e.getMessage();
        } catch (NotAuthorizedException e) {
            logger.error(e.getMessage(), e);
            return Keywords.AUTH_EXCEPTION + e.getMessage();
        }catch (DBException e) {
            logger.error(e.getMessage(), e);
            return Keywords.EVENT_UNSUB_EXCEPTION + e.getMessage();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Keywords.EXCEPTION;
        }
    }

    /**
     * Метод для преобразования списка мероприятий в удобный для чтения формат
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