package controllerTests;

import controller.Keywords;
import controller.UserController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTests {

    public static UserController userController = new UserController();

    @Test
    public void createUser(){
        var result = userController.create("dbTest", "dbTest");
        assertEquals("user dbTest added", result);
    }

    @Test
    public void createExistingUser(){
        var result = userController.create("name123", "dbTest");
        assertEquals(Keywords.exception, result);
    }

    @Test
    public void createUserShortPassword(){
        var result = userController.create("dbTestik", "12");
        assertEquals(Keywords.shortPass, result);
    }

    @Test
    public void createUserLongName(){
        var result = userController.create("dbTestikdbTestikdbTestikdbTestikdbTestikdbTestikdbTestikdbTestikd", "12");
        assertEquals(Keywords.longName, result);
    }

    @Test
    public void logUserIn(){
        var result = userController.logIn("dbTest", "dbTest");
        assertEquals("Welcome, user dbTest", result);
    }

    @Test
    public void logInWrongPassword(){
        var result = userController.logIn("dbTest", "12345");
        assertEquals(Keywords.wrongPass, result);
    }

    @Test
    public void logInWrongUser(){
        var result = userController.logIn("Th1$U$eЯD0$И0TEx1$T", "12345");
        assertEquals(Keywords.wrongUser, result);
    }

    @Test
    public void signUser(){
        userController.logIn("dbTest", "dbTest");
        var result = userController.signUp("event123");
        assertEquals("dbTest signed up for event123", result);
    }

    @Test
    public void getCurrentUser(){
        userController.logIn("dbTest", "dbTest");
        var current = userController.getCurrent();
        assertEquals("dbTest", current.getName());
    }
}
