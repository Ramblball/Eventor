package controller;

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
    //Слишком длинное место проведения
    public final static String longPlace = "Описание мета проведения должно быть короче 128 символов";
    //Слишком длинное описание
    public final static String longDesc = "Описание должно быть короче 512 символов";
    //Внутренняя ошибка модели
    public final static String exception = "Непредвиденная ошибка...";
    //id не парсится как число
    public final static String idNotNumb = "Id должно быть числом";
    //Пользователь пытается именить чужое мероприятие
    public final static String notOwnUpdate = "Вы не можете изменять мероприятия других пользователей";
    //Не найдено мероприятие с введенным id
    public final static String eventNotFound = "Мероприятие не найдено";

    /*
    ----------------- Форматы данных --------------------------------
     */

    //Формат ввода даты
    public final static String dateTimeFormat = "yyyy-MM-dd HH:mm";

    /*
    ----------------- Помощь --------------------------------
     */
}
