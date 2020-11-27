package database;

public final class DBLiterals {
    public final static String userTable = "\"user\"";
    public final static String eventTable = "event";
    public final static String eventorSchema = "eventor_schema";
    public final static String usersEvents = "users_events";

    public final static String userIdSeq = "user_id_seq";
    public final static String eventIdSeq = "event_id_seq";

    public final static String usersWithSubscribes = "users_with_subscribes";
    public final static String userWithCreated = "user_with_created";

    public final static String subscribes = "subscribes";
    public final static String createdEvents = "createdEvents";
    public final static String user = "user";

    public final static String userId = "user_id";
    public final static String eventId = "event_id";
    public final static String name = "name";
    public final static String description = "description";

    public final static String dateTimeFormat = "yyyy-MM-dd HH:mm";

    public final static String findByNameQuery = "FROM User WHERE name=:name";

    public final static String createEventSetVectorQuery =
            "INSERT INTO eventor_schema.event_vector (event_id, event_description) VALUES (:event_id, to_tsvector(:description))";
    public static final String updateEventUpdateVectorQuery =
            "UPDATE eventor_schema.event_vector SET event_description = to_tsvector(:description) WHERE event_id = :event_id";
}
