package database.utils;

public class QueryLiterals {
    public static final String nameQuery = "name LIKE '%";
    public static final String placeQuery = "place LIKE '%";
    public static final String descriptionQuery = "event_description @@ plainto_tsquery('";
    public static final String categoryQuery = "category = ";
    public static final String executedQuery = "SELECT eventor_schema.event.* FROM eventor_schema.event " +
            "LEFT JOIN eventor_schema.event_vector ev ON event.id = ev.event_id WHERE ";
    public static final String orderQuery = "ORDER BY time";

    public static final String percent = "%'";
    public static final String following = "<->";
    public static final String bracket = "')";
    public static final String and = " AND ";
}
