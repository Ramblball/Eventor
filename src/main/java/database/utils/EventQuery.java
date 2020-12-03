package database.utils;

import org.hibernate.QueryParameterException;

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
        this.name = NAME_QUERY + name + PERCENT;
    }

    public void setPlace(String place) {
        if (place.length() == 0) return;
        this.place = PLACE_QUERY + place + PERCENT;
    }

    public void setTime(String time) {
        try {
            LocalDate date = LocalDate.parse(time, DateTimeFormatter.ofPattern(DATE_PATTERN));
            StringBuilder builder = new StringBuilder();
            builder.append(TIME_QUERY_1);
            builder.append(date);
            builder.append(TIME_QUERY_2);
            builder.append(date);
            builder.append(TIME_QUERY_3);
            this.time = builder.toString();
        } catch (DateTimeParseException e) {
            this.time = null;
        }
    }

    public void setDescription(String description) {
        if (description.length() == 0) return;
        String[] words = description.split("[ ]");
        StringBuilder builder = new StringBuilder();
        builder.append(DESCRIPTION_QUERY);
        for (String word : words) {
            builder.append(word);
            builder.append(FOLLOWING);
        }
        int last = builder.lastIndexOf(FOLLOWING);
        builder.delete(last, last + 3);
        builder.append(BRACKET);
        this.description = builder.toString();
    }

    public void setCategory(String category) {
        if (category.length() == 0) return;
        try {
            this.category = CATEGORY_QUERY + Integer.parseInt(category);
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
    public String execute() throws QueryParameterException{
        StringBuilder builder = new StringBuilder();
        if (isEmpty()) {
            throw new QueryParameterException("Попытка выполнить запрос без параметров");
        }
        builder.append(EXECUTED_QUERY);
        if (name != null) {
            builder.append(name);
            builder.append(AND);
        }
        if (place != null) {
            builder.append(place);
            builder.append(AND);
        }
        if (time != null){
            builder.append(time);
            builder.append(AND);
        }
        if (description != null) {
            builder.append(description);
            builder.append(AND);
        }
        if (category != null) {
            builder.append(category);
            builder.append(AND);
        }
        int last = builder.lastIndexOf(AND);
        builder.delete(last, last + 4);
        builder.append(ORDER_QUERY);
        return builder.toString();
    }
}