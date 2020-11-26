package controller;

import database.model.User;

public abstract class Controller {
    protected static User currentUser;

    /**
     * Check that user is not authorized
     * @return authorized user or not
     */
    protected boolean isLogout() {
        return currentUser == null;
    }
}
