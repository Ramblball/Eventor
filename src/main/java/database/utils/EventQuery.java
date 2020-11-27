package database.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static database.utils.QueryLiterals.*;

/**
 * Класс для сборки SQL запроса на поиск с критериями
 */
public class EventQuery {
    private String name;
    private String place;
    private String time;
    private String description;
    private String category;

    public void setName(String name) {
        if (name.length() == 0) return;
        this.name = nameQuery + name + percent;
    }

    public void setPlace(String place) {
        if (place.length() == 0) return;
        this.place = placeQuery + place + percent;
    }

    public void setTime(String time) {
        try {
            LocalDate date = LocalDate.parse(time, DateTimeFormatter.ofPattern(datePattern));
            StringBuilder builder = new StringBuilder();
            builder.append(timeQuery1);
            builder.append(date);
            builder.append(timeQuery2);
            builder.append(date);
            builder.append(timeQuery3);
            this.time = builder.toString();
        } catch (DateTimeParseException e) {
            this.time = null;
        }
    }

    public void setDescription(String description) {
        if (description.length() == 0) return;
        String[] words = description.split("[ ]");
        StringBuilder builder = new StringBuilder();
        builder.append(descriptionQuery);
        for (String word : words) {
            builder.append(word);
            builder.append(following);
        }
        int last = builder.lastIndexOf(following);
        builder.delete(last, last + 3);
        builder.append(bracket);
        this.description = builder.toString();
    }

    public void setCategory(String category) {
        if (category.length() == 0) return;
        try {
            this.category = categoryQuery + Integer.parseInt(category);
        } catch (NumberFormatException e) {
            this.category = null;
        }
    }

    /**
     * Проверка, что хотя бы одно из полей запроса не пустое
     * @return            Результат проверки
     */
    public boolean isEmpty() {
        return name == null && place == null && time == null && description == null && category == null;
    }

    /**
     * Собирает SQL запрос из заданных полей
     * @return            Строка SQL запроса к базе данных
     */
    public String execute() {
        StringBuilder builder = new StringBuilder();
        if (isEmpty()) {
            throw new NullPointerException();
        }
        builder.append(executedQuery);
        if (name != null) {
            builder.append(name);
            builder.append(and);
        }
        if (place != null) {
            builder.append(place);
            builder.append(and);
        }
        if (time != null){
            builder.append(time);
            builder.append(and);
        }
        if (description != null) {
            builder.append(description);
            builder.append(and);
        }
        if (category != null) {
            builder.append(category);
            builder.append(and);
        }
        int last = builder.lastIndexOf(and);
        builder.delete(last, last + 4);
        builder.append(orderQuery);
        return builder.toString();
    }
}