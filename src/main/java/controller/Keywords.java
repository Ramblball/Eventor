package controller;

/**
 * Класс с литералами для контроллеров
 */
public final class Keywords {
    /*
    ----------------- Успешное выполнение --------------------------------
     */
    //Пользователь создан
    public final static String userCreated = "Добро пожаловать, '%s'";
    //Пользователь обновлен
    public final static String userUpdated = "Ваше имя обновлено на '%s'";
    //Мероприятие создано
    public final static String eventCreated = "Мероприятие '%s' создано";
    //Мероприятие обновлено
    public final static String eventUpdated = "Мероприятие '%s' изменено";
    //Мероприятие удалено
    public final static String eventRemoved = "Мероприятие '%s' удалено";
    //Пользователь подписался на мероприятие
    public final static String eventSigned = "Вы добавлены в список участников";
    //Пользователь отписался от мероприятия
    public final static String eventUnsigned = "Вы удалены из списка участников";
    /*
    ----------------- Ошибки при выполнении --------------------------------
     */
    //Слишком длинное название
    public final static String longName = "Название должно быть короче 32 символов";
    //Неверный формат ввода даты
    public final static String invalidTime = "Используйте формат yyyy-MM-dd HH:mm для задания времени";
    //Дата и время уже прошли
    public final static String prevDate = "Введенная дата уже прошла";
    //Слишком длинное место проведения
    public final static String longPlace = "Описание мета проведения должно быть короче 128 символов";
    //Слишком длинное описание
    public final static String longDesc = "Описание должно быть короче 512 символов";
    //Внутренняя ошибка модели
    public final static String exception = "Непредвиденная ошибка...";
    //Пользователь пытается именить чужое мероприятие
    public final static String notOwnUpdate = "Вы не можете изменять мероприятия других пользователей";
    //Не найдено мероприятие с введенным id
    public final static String eventNotFound = "Мероприятие не найдено";
    //Юзер с таким именем уже существует
    public final static String userAlreadyExist = "Вы уже зарегистрированы";
    //Список мероприятий пуст
    public final static String noEvents = "У вас нет мероприятий";
    //пользователя нет в бд
    public final static String userNotFoundException = "Пользователь не найден";
    //Ошибка валидации
    public final static String validationException = "Ошибка в введеных данных:\n";
    //Ошибка создания пользователя
    public final static String userCreateException = "Не удалось создать пользователя:\n";
    //Ошибка обновления пользователя
    public final static String userUpdateException = "Не удалось сохранить изменения:\n";
    //Пользователь не авторизован
    public final static String authException = "Не удалось найти ваши данные, введите /start";
    //Ошибка создания меропрития
    public final static String eventCreateException = "Не удалось создать мероприятие:\n";
    //Ошибка обновления меропрития
    public final static String eventUpdateException = "Не удалось обновить мероприятие:\n";
    //Ошибка удаления меропрития
    public final static String eventRemoveException = "Не удалось удалить мероприятие:\n";
    //Ошибка поиска меропритий
    public final static String eventFindByException = "Не удалось найти ваши мероприятия:\n";
    //Ошибка поиска меропрития
    public final static String eventFindException = "Не удалось найти ваши мероприятия:\n";
    //Ошибка подписки на меропритие
    public final static String eventSubException = "Не удалось оформить подписку:\n";
    //Ошибка отписки от меропрития
    public final static String eventUnsubException = "Не удалось отписаться:\n";
    /*
    ----------------- Форматы данных --------------------------------
     */
    //Формат ввода даты
    public final static String dateTimeFormat = "yyyy-MM-dd HH:mm";
}