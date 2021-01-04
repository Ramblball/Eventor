package controller;

/**
 * Класс с литералами для контроллеров
 */
public final class Keywords {
    /*
    ----------------- Успешное выполнение --------------------------------
     */
    //Пользователь создан
    public final static String USER_CREATED = "Добро пожаловать, '%s'";
    //Пользователь обновлен
    public final static String USER_UPDATED = "Ваше имя изменено на '%s'";
    //Мероприятие создано
    public final static String EVENT_CREATED = "Мероприятие '%s' создано";
    //Мероприятие обновлено
    public final static String EVENT_UPDATED = "Мероприятие '%s' изменено";
    //Мероприятие удалено
    public final static String EVENT_REMOVED = "Мероприятие '%s' удалено";
    //Пользователь подписался на мероприятие
    public final static String EVENT_SIGNED = "Вы добавлены в список участников";
    //Пользователь отписался от мероприятия
    public final static String EVENT_UNSIGNED = "Вы удалены из списка участников";
    /*
    ----------------- Ошибки при выполнении --------------------------------
     */
    //Слишком длинное название
    public final static String LONG_NAME = "Название должно быть короче 32 символов";
    //Дата и время уже прошли
    public final static String PAST_DATE = "Введенная дата уже прошла";
    //Слишком длинное описание
    public final static String LONG_DESCRIPTION = "Описание должно быть короче 512 символов";
    //Внутренняя ошибка модели
    public final static String EXCEPTION = "Непредвиденная ошибка...";
    //Пользователь пытается изменить чужое мероприятие
    public final static String NOT_CREATED_UPDATE = "Вы не можете изменять мероприятия других пользователей";
    //Не найдено мероприятие с введенным названием
    public final static String EVENT_NOT_FOUND = "Мероприятие не найдено";
    //Список мероприятий пуст
    public final static String NO_EVENTS = "У вас нет мероприятий";
    //Пользователя нет в базе данных
    public final static String USER_NOT_FOUND_EXCEPTION = "Пользователь не найден:\n";
    //Ошибка валидации
    public final static String VALIDATION_EXCEPTION = "Ошибка в введенных данных:\n";
    //Ошибка создания пользователя
    public final static String USER_CREATE_EXCEPTION = "Не удалось создать пользователя:\n";
    //Ошибка обновления пользователя
    public final static String USER_UPDATE_EXCEPTION = "Не удалось сохранить изменения:\n";
    //Пользователь не авторизован
    public final static String AUTH_EXCEPTION = "Не удалось найти ваши данные, введите /start";
    //Ошибка создания мероприятия
    public final static String EVENT_CREATE_EXCEPTION = "Не удалось создать мероприятие:\n";
    //Ошибка обновления мероприятия
    public final static String EVENT_UPDATE_EXCEPTION = "Не удалось обновить мероприятие:\n";
    //Ошибка удаления мероприятия
    public final static String EVENT_REMOVE_EXCEPTION = "Не удалось удалить мероприятие:\n";
    //Ошибка поиска мероприятия
    public final static String EVENT_FIND_EXCEPTION = "Не удалось найти ваши мероприятия:\n";
    //Ошибка подписки на мероприятия
    public final static String EVENT_SUB_EXCEPTION = "Не удалось оформить подписку:\n";
    //Ошибка отписки от мероприятия
    public final static String EVENT_UNSUB_EXCEPTION = "Не удалось отписаться:\n";
    /*
    ----------------- Форматы данных --------------------------------
     */
    //Формат ввода даты
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
}