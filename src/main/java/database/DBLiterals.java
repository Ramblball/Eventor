package database;

/**
 * Класс с литералами для слоев связи с базой данных
 */
public final class DBLiterals {
    /*
    ----------------- Таблицы и схемы --------------------------------
     */
    //Название таблицы с пользователями
    public final static String USER_TABLE = "\"user\"";
    //Название таблицы с мероприятиями
    public final static String EVENT_TABLE = "event";
    //Название схемы с таблицами проекта
    public final static String EVENTOR_SCHEMA = "eventor_schema";
    //Название таблицы связи пользователей и мероприятий
    public final static String USERS_EVENTS_TABLE = "users_events";
    /*
    ----------------- Генераторы последовательностей --------------------------------
     */
    //Название генератора id мероприятий
    public final static String EVENT_ID_SEQ = "event_id_seq";
    /*
    ----------------- Профили подгрузки --------------------------------
     */
    //Название fetch профиля для получения мероприятий, в которых пользователь учавствует
    public final static String USERS_WITH_SUBSCRIBES = "users_with_subscribes";
    //Название fetch профиля для получения мероприятий, созданных пользоваелем
    public final static String USER_WITH_CREATED = "user_with_created";
    /*
    ----------------- Поля для маппинга --------------------------------
     */
    //Название поля с мероприятиями, в которых пользователь учавствует в User
    public final static String SUBSCRIBES = "subscribes";
    //Название поля с мероприятиями, созданных пользоваелем в User
    public final static String CREATED_EVENTS = "createdEvents";
    //название поля с создателем мероприятия в Event
    public final static String USER = "user";
    /*
    ----------------- Названия столбцов --------------------------------
     */
    //Столбец с id создателя мероприятия в event, users_events
    public final static String USER_ID = "user_id";
    //Столбец с id мероприятия в users_events
    public final static String EVENT_ID = "event_id";
    /*
    ----------------- Форматы данных --------------------------------
     */
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    /*
    ----------------- Поля для вставки параметров --------------------------------
    */
    //Поле имени при поиске
    public final static String NAME = "name";
    //Поле описания при создании вектора
    public final static String DESCRIPTION = "description";
    /*
    ----------------- SQL запросы --------------------------------
     */
    //Запрос на поиск пользователя по имени
    public final static String FIND_BY_NAME_QUERY = "FROM User WHERE name=:name";
    //Запрос создание вектора, при создании мероприятия
    public final static String CREATE_EVENT_SET_VECTOR_QUERY =
            "INSERT INTO eventor_schema.event_vector (event_id, event_description) VALUES (:event_id, to_tsvector(:description))";
    //Запрос на обновление вектра, при обновлении мероприятия
    public static final String UPDATE_EVENT_UPDATE_VECTOR_QUERY =
            "UPDATE eventor_schema.event_vector SET event_description = to_tsvector(:description) WHERE event_id = :event_id";
    /*
    ----------------- Ошибки при обращнии к бд --------------------------------
     */
    //Ошибка обращения к бд
    public static final String DB_EXCEPTION = "Ошибка при обращении к базе данных:";
    //Ошибка, пользователь не найден
    public static final String USER_NOT_EXIST = "Пользовтеля не существует";
    //Ошибка, пользователь уже создан
    public static final String USER_ALREADY_EXIST_EXCEPTION = "Пользовтеля уже создан";
    //Ошибка, мероприятие не найдено
    public static final String EVENT_NOT_FOUND = "Мероприятий не найдено";
    //Ошибка, попытка подписаться на собственное мероприятие
    public static final String USER_CREATOR = "Вы являетесь создаталем мероприятия";
}