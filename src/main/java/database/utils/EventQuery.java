package database.utils;

import org.hibernate.QueryParameterException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.StringJoiner;

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
            this.time = TIME_QUERY_1 +
                    date +
                    TIME_QUERY_2 +
                    date +
                    TIME_QUERY_3;
        } catch (DateTimeParseException e) {
            this.time = null;
        }
    }

    public void setDescription(String description) {
        if (description.length() == 0) return;
        String[] words = description.split("[ ]");
        StringJoiner builder = new StringJoiner(FOLLOWING);
        builder.add(DESCRIPTION_QUERY);
        for (String word : words) {
            builder.add(word);
        }
        builder.add(BRACKET);
        this.description = builder.toString();
    }

    public void setCategory(String category) {
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
        StringJoiner builder = new StringJoiner(AND);
        if (isEmpty()) {
            throw new QueryParameterException("Попытка выполнить запрос без параметров");
        }
        builder.add(EXECUTED_QUERY);
        if (name != null) {
            builder.add(name);
        }
        if (place != null) {
            builder.add(place);
        }
        if (time != null){
            builder.add(time);
        }
        if (description != null) {
            builder.add(description);
        }
        if (category != null) {
            builder.add(category);
        }
        builder.add(ORDER_QUERY);
        return builder.toString();
    }
}