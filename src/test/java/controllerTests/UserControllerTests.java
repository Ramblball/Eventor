package controllerTests;

import controller.EventController;
import controller.Keywords;
import controller.UserController;
import database.model.Category;
import database.model.Event;
import database.model.User;
import database.services.EventService;
import database.services.UserService;
import dbTests.TestSessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTests {
    UserController controller;
    User user;
    String pass;
    Event event;
    Event tEvent;
    User tUser;

    @BeforeAll
    public void beforeAll() {
        controller = new UserController();
        user = new User();
        user.setName("user");
        pass = "pass";
        user.setPassword(pass);
        event = new Event("event", "place", LocalDateTime.now(), Category.Прогулка, "description");
        try (Session session = TestSessionFactory.getInstance().openSession()) {
            session.getTransaction().begin();
            session.save(user);
            session.getTransaction().commit();
            session.getTransaction().begin();
            user.addCreatedEvent(event);
            session.save(event);
            session.getTransaction().commit();
        }
    }

    @AfterEach
    public void afterEach() {
        controller.logOut();
    }

    @AfterAll
    public void afterAll() {
        try (Session session = TestSessionFactory.getInstance().openSession()) {
            session.getTransaction().begin();
            session.refresh(user);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Test
    @DisplayName("create: user created")
    public void createTest1() {
        String result = controller.create("temp", "pass");
        assertEquals("user temp added", result);
    }

    @Test
    @DisplayName("create: name is too long")
    public void createTest2() {
        String result = controller.create("a".repeat(35), "pass");
        assertEquals(Keywords.longName, result);
    }

    @Test
    @DisplayName("create: short password")
    public void createTest3() {
        String result = controller.create("temp", "e");
        assertEquals(Keywords.shortPass, result);
    }

    @Test
    @DisplayName("update: user updated")
    public void updateTest1() {
        controller.logIn(user.getName(), pass);
        pass = "root";
        String result = controller.update("user", pass);
        assertEquals(Keywords.userUpdateResult, result);
    }

    @Test
    @DisplayName("subscribeManager: event not found")
    public void subscribeManagerTest1() {
        controller.logIn(user.getName(), pass);
        assertEquals(Keywords.eventNotFound, controller.subscribeManager("0", true));
    }

    @Test
    @DisplayName("subscribeManager: id is not a number")

    public void subscribeManagerTest2() {
        controller.logIn(user.getName(), pass);
        assertEquals(Keywords.idNotNumb, controller.subscribeManager("id", true));
    }

    @Test
    @DisplayName("subscribeManager: user unauthorized")
    public void subscribeManagerTest3() {
        assertEquals(Keywords.unLogin, controller.subscribeManager(event.getId().toString(), true));
    }

    @Test
    @DisplayName("logIn: user logged in")
    public void logInTest1() {
        assertEquals("Welcome, " + user.getName(),
                controller.logIn(user.getName(), pass));
    }

    @Test
    @DisplayName("logIn: no such user")
    public void logInTest2() {
        assertEquals(Keywords.wrongUser, controller.logIn("wrong", pass));
    }

    @Test
    @DisplayName("logIn: invalid password")
    public void logInTest3() {
        assertEquals(Keywords.wrongPass, controller.logIn(user.getName(), "e"));
    }
}
