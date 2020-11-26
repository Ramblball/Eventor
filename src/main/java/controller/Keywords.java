package controller;

public final class Keywords {
    public final static String user = "user ";
    public final static String event = "event ";

    public final static String added = " added";
    public final static String found = "found ";
    public final static String updated = " updated";
    public final static String removed = " removed";
    public final static String signed = " signed up for ";
    public final static String unsigned = " unsigned from ";
    public final static String welcome = "Welcome, ";
    public final static String userUpdateResult = "User settings updated";

    public final static String wrongPass = "No such user";
    public final static String wrongUser = "Invalid password";
    public final static String shortPass = "Password must be longer";
    public final static String longName = "Too much long name";
    public final static String longPlace = "Too much long place name";
    public final static String longDesc = "Too much long description";
    public final static String exception = "Something going wrong...";
    public final static String unLogin = "You need to authorize";
    public final static String idNotNumb = "Id is not a number";
    public final static String noEventId = "There is no event with this id";
    public final static String notOwnUpdate = "You can not update other peoples events";
    public final static String invalidTime = "Use yyyy-MM-dd HH:mm format for time";

    public final static String dateTimeFormat = "yyyy-MM-dd HH:mm";

    public final static String help = "This is a bot for creating events.\nTo create a user, type: \"create user *name* *password*\"\nTo log in, type: \"login *name* *password*\"\nTo create an event, type: \"create event *name* *time:(yyyy-MM-dd HH:mm)* *place* *description*\"\nTo find an event, type: \"find *eventName\"\nTo sign up for an event, type: \"signup *event id*\"";
}
