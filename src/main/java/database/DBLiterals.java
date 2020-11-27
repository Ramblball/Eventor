package database;

/**
 * Класс с литералами для слоев связи с базой данных
 */
public final class DBLiterals {
    /*
    ----------------- Таблицы и схемы --------------------------------
     */
    //Название таблицы с пользователями
    public final static String userTable = "\"user\"";
    //Название таблицы с мероприятиями
    public final static String eventTable = "event";
    //Название схемы с таблицами проекта
    public final static String eventorSchema = "eventor_schema";
    //Название таблицы связи пользователей и мероприятий
    public final static String usersEventsTable = "users_events";
    /*
    ----------------- Генераторы последовательностей --------------------------------
     */
    //Название генератора id пользователей
    public final static String userIdSeq = "user_id_seq";
    //Название генератора id мероприятий
    public final static String eventIdSeq = "event_id_seq";
    /*
    ----------------- Профили подгрузки --------------------------------
     */
    //Название fetch профиля для получения мероприятий, в которых пользователь учавствует
    public final static String usersWithSubscribes = "users_with_subscribes";
    //Название fetch профиля для получения мероприятий, созданных пользоваелем
    public final static String userWithCreated = "user_with_created";
    /*
    ----------------- Поля для маппинга --------------------------------
     */
    //Название поля с мероприятиями, в которых пользователь учавствует в User
    public final static String subscribes = "subscribes";
    //Название поля с мероприятиями, созданных пользоваелем в User
    public final static String createdEvents = "createdEvents";
    //название поля с создателем мероприятия в Event
    public final static String user = "user";
    /*
    ----------------- Названия столбцов --------------------------------
     */
    //Столбец с id создателя мероприятия в event, users_events
    public final static String userId = "user_id";
    //Столбец с id мероприятия в users_events
    public final static String eventId = "event_id";
    /*
    ----------------- Форматы данных --------------------------------
     */
    public final static String dateTimeFormat = "yyyy-MM-dd HH:mm";
    /*
    ----------------- Поля для вставки параметров --------------------------------
    */
    //Поле имени при поиске
    public final static String name = "name";
    //Поле описания при создании вектора
    public final static String description = "description";
    /*
    ----------------- SQL запросы --------------------------------
     */
    //Запрос на поиск пользователя по имени
    public final static String findByNameQuery = "FROM User WHERE name=:name";
    //Запрос создание вектора, при создании мероприятия
    public final static String createEventSetVectorQuery =
            "INSERT INTO eventor_schema.event_vector (event_id, event_description) VALUES (:event_id, to_tsvector(:description))";
    //Запрос на обновление вектра, при обновлении мероприятия
    public static final String updateEventUpdateVectorQuery =
            "UPDATE eventor_schema.event_vector SET event_description = to_tsvector(:description) WHERE event_id = :event_id";
}