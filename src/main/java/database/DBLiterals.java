package database;

public class DBLiterals {
    public static final String userTable = "\"user\"";
    public static final String eventTable = "event";
    public static final String eventorSchema = "eventor_schema";
    public static final String usersEvents = "users_events";

    public static final String userIdSeq = "user_id_seq";
    public static final String eventIdSeq = "event_id_seq";

//    public static final String enumTypePostgreSQL = "database.type.EnumTypePostgreSQL";

    public static final String usersWithSubscribes = "users_with_subscribes";
    public static final String userWithCreated = "user_with_created";

    public static final String subscribes = "subscribes";
    public static final String createdEvents = "createdEvents";
    public static final String user = "user";

    public static final String userId = "user_id";
    public static final String eventId = "event_id";
    public static final String name = "name";
    public static final String description = "description";

    public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    public static final String findByNameQuery = "FROM User WHERE name=:name";
    public static final String createEventSetVectorQuery =
            "INSERT INTO eventor_schema.event_vector (event_id, event_description) VALUES (:event_id, to_tsvector(:description))";
}
