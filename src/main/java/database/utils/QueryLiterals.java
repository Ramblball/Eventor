package database.utils;

/**
 * Класс с литералами для EventQuery
 */
public class QueryLiterals {
    /*
    ----------------- Запросы --------------------------------
     */
    //Поиск схожего имени
    public static final String nameQuery = "name LIKE '%";
    //Поиск схожего места
    public static final String placeQuery = "place LIKE '%";
    //FTS по описанию
    public static final String descriptionQuery = "event_description @@ plainto_tsquery('";
    //Поиск по заданной категории
    public static final String categoryQuery = "category = ";
    //Поиск по заданной дате
    public static final String timeQuery1 = "time >= date '";
    public static final String timeQuery2 = "' AND time < (date '";
    public static final String timeQuery3 = "' + interval '1 day')";
    //Заголовок поиского запроса
    public static final String executedQuery = "SELECT eventor_schema.event.* FROM eventor_schema.event " +
            "LEFT JOIN eventor_schema.event_vector ev ON event.id = ev.event_id WHERE ";
    //Сортировка по врмени
    public static final String orderQuery = "ORDER BY time";
    /*
    ----------------- Связки --------------------------------
    */
    //Окончание для поиска по имени и месту
    public static final String percent = "%'";
    //Оператор следования для FTS
    public static final String following = "<->";
    //Скобочка
    public static final String bracket = "')";
    //Объединение критериев поиска
    public static final String and = " AND ";
    /*
    ----------------- Форматы данных --------------------------------
    */
    //Формат даты
    public static final String datePattern = "yyyy-MM-dd";
}