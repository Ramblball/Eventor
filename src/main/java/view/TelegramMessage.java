package view;

import org.telegram.telegrambots.meta.api.objects.User;

/**
 * Класс сообщения для обработки сообщений от пользователя
 */
public class TelegramMessage {
    private final User user;
    private String operation;
    private String eventName;
    private String eventTime;
    private String eventPlace;
    private String eventLimit;
    private String eventDescription;

    public TelegramMessage(User user) {
        this.user = user;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventLimit() {
        return eventLimit;
    }
    public void setEventLimit(String eventLimit) {
        this.eventLimit = eventLimit;
    }

    public User getUser() {
        return user;
    }
}
