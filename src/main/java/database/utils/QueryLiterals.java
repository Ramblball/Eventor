package database.utils;

/**
 * Класс с литералами для EventQuery
 */
public final class QueryLiterals {
    /*
    ----------------- Запросы --------------------------------
     */
    //Поиск схожего имени
    public static final String NAME_QUERY = "name LIKE '%";
    //Поиск в радиусе километра на карте
    public static final String PLACE_QUERY_1 = "( acos(sin(latitude * 0.0175) * sin(";
    public static final String PLACE_QUERY_2 = " * 0.0175 + cos(latitude * 0.0175) * cos() ";
    public static final String PLACE_QUERY_3 = " * 0.0175) * cos((";
    public static final String PLACE_QUERY_4 = " * 0.0175) - (longitude * 0.0175))) <= 1000 )";
    //FTS по описанию
    public static final String DESCRIPTION_QUERY = "event_description @@ plainto_tsquery('";
    //Поиск по заданной категории
    public static final String CATEGORY_QUERY = "category = ";
    //Поиск по заданной дате
    public static final String TIME_QUERY_1 = "time >= date '";
    public static final String TIME_QUERY_2 = "' AND time < (date '";
    public static final String TIME_QUERY_3 = "' + interval '1 day')";
    //Заголовок поискового запроса
    public static final String EXECUTED_QUERY = "SELECT eventor_schema.event.* FROM eventor_schema.event " +
            "LEFT JOIN eventor_schema.event_vector ev ON event.id = ev.event_id WHERE ";
    //Сортировка по времени
    public static final String ORDER_QUERY = "ORDER BY time";
    /*
    ----------------- Связки --------------------------------
    */
    //Окончание для поиска по имени и месту
    public static final String PERCENT = "%'";
    //Оператор следования для FTS
    public static final String FOLLOWING = "<->";
    //Скобочка
    public static final String BRACKET = "')";
    //Объединение критериев поиска
    public static final String AND = " AND ";
    /*
    ----------------- Форматы данных --------------------------------
    */
    //Формат даты
    public static final String DATE_PATTERN = "yyyy-MM-dd";
}