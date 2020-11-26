package controller;

import database.model.Event;
import database.model.User;
import database.services.EventService;
import database.services.UserService;

public class UserController extends Controller{
    private final EventService eventService = new EventService();
    private final UserService userService = new UserService();
    private StringBuilder stringBuilder = new StringBuilder();

    /**
     * Create new User
     *
     * @param name     of new user
     * @param password of new user
     * @return success of adding user
     */
    public String create(String name, String password) {
        if (password.length() < 4) {
            return Keywords.shortPass;
        }
        if (name.length() > 32) {
            return Keywords.longName;
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        boolean result = userService.save(user);
        if (!result) {
            return Keywords.exception;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.user);
        stringBuilder.append(user.getName());
        stringBuilder.append(Keywords.added);
        return stringBuilder.toString();
    }

    /**
     * Update name of current user
     *
     * @param name new name of user
     * @return result of updating
     */
    public String update(String name, String password) {
        if (isLogout()) {
            return Keywords.unLogin;
        }
        if (password.length() < 4) {
            return Keywords.shortPass;
        }
        if (name.length() > 32) {
            return Keywords.longName;
        }
        currentUser.setName(name);
        currentUser.setPassword(password);
        boolean result = userService.update(currentUser);
        if (!result) {
            return Keywords.exception;
        }
        return Keywords.userUpdateResult;
    }

    /**
     * Manage users subscribes
     *
     * @param id of event user want to sign on
     * @param f user want to sign in or sign out
     * @return result of operation
     */
    public String subscribeManager(String id, boolean f) {
        try {
            if (isLogout()) {
                return Keywords.unLogin;
            }
            Event event;
            event = eventService.findById(Integer.parseInt(id));
            if (event == null) {
                return Keywords.exception;
            }
            boolean result;
            if (f) {
                result = userService.subscribe(currentUser, event);
            } else {
                result = userService.unsubscribe(currentUser, event);
            }
            if (!result) {
                return Keywords.exception;
            }
        } catch (NumberFormatException e) {
            return Keywords.idNotNumb;
        }
        return null;
    }

    /**
     * Subscribe user to event
     *
     * @param id Id of event
     * @return Result of operation
     */
    public String signIn(String id) {
        String result = subscribeManager(id, true);
        if (result == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(currentUser.getName());
            stringBuilder.append(Keywords.signed);
            stringBuilder.append(id);
            return stringBuilder.toString();
        }
        return result;
    }

    /**
     * Unsubscribe user from event
     *
     * @param id Id of event
     * @return Result of operation
     */
    public String signOut(String id) {
        String result = subscribeManager(id, false);
        if (result == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(currentUser.getName());
            stringBuilder.append(Keywords.unsigned);
            stringBuilder.append(id);
            return stringBuilder.toString();
        }
        return result;
    }

    /**
     * Check that user exist and give it
     * possibility to work in system
     *
     * @param name     of existing user
     * @param password of existing user
     * @return success of logging
     */
    public String logIn(String name, String password) {
        User user = userService.findByName(name);
        if (user == null)
            return Keywords.wrongUser;
        if (!user.checkPassword(password))
            return Keywords.wrongPass;
        currentUser = user;
        stringBuilder = new StringBuilder();
        stringBuilder.append(Keywords.welcome);
        stringBuilder.append(currentUser.getName());
        return stringBuilder.toString();
    }

    /**
     * Logs out
     * @return status of log
     */
    public String logOut(){
        if(currentUser == null)
            return "You are not logged in";
        currentUser = null;
        return "You are logged out";
    }
}
