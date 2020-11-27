package controllerTests;

import controller.EventController;
import controller.Keywords;
import controller.UserController;
import database.model.Category;
import database.model.Event;
import database.model.User;
import database.utils.EventQuery;
import dbTests.TestSessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventControllerTests {
//    EventController controller;
//    User user;
//    Event event;
//    Event tEvent;
//
//    @BeforeAll
//    public void beforeAll() {
//        controller = new EventController();
//        user = new User();
//        user.setName("user");
//        event = new Event("event", "place", LocalDateTime.now(), Category.Прогулка, "description");
//        try (Session session = TestSessionFactory.getInstance().openSession()) {
//            session.getTransaction().begin();
//            session.save(user);
//            session.getTransaction().commit();
//            session.getTransaction().begin();
//            user.addCreatedEvent(event);
//            session.save(event);
//            session.getTransaction().commit();
//        }
//        new UserController().logIn(user.getName(), "pass");
//    }
//
//    @AfterAll
//    public void afterAll() {
//        try (Session session = TestSessionFactory.getInstance().openSession()) {
//            session.getTransaction().begin();
//            session.refresh(user);
//            session.delete(user);
//            session.getTransaction().commit();
//        }
//    }
//
//    @Test
//    @DisplayName("getHelp: help returned")
//    public void getHelpTest1() {
//        assertEquals(controller.getHelp(), Keywords.help);
//    }
//
//    @Test
//    @DisplayName("checkEventsParams: params are valid")
//    public void checkEventsParamsTest1() {
//        assertNull(controller.checkEventsParams(
//                "event",
//                "2020-11-26 20:00",
//                "place",
//                "description"
//        ));
//    }
//
//    @Test
//    @DisplayName("checkEventsParams: name is too long")
//    public void checkEventsParamsTest2() {
//        assertEquals(Keywords.longName, controller.checkEventsParams(
//                "a".repeat(40),
//                "2020-11-26 20:00",
//                "place",
//                "description"
//        ));
//    }
//
//    @Test
//    @DisplayName("checkEventsParams: unparsed time")
//    public void checkEventsParamsTest3() {
//        assertEquals(Keywords.invalidTime, controller.checkEventsParams(
//                "event",
//                "2020-11-26_20:00",
//                "place",
//                "description"
//        ));
//    }
//
//    @Test
//    @DisplayName("checkEventsParams: place is too long")
//    public void checkEventsParamsTest4() {
//        assertEquals(Keywords.longPlace, controller.checkEventsParams(
//                "event",
//                "2020-11-26 20:00",
//                "a".repeat(130),
//                "description"
//        ));
//    }
//
//    @Test
//    @DisplayName("checkEventsParams: description is too long")
//    public void checkEventsParamsTest5() {
//        assertEquals(Keywords.longDesc, controller.checkEventsParams(
//                "event",
//                "2020-11-26 20:00",
//                "place",
//                "a".repeat(520)
//        ));
//    }
//
//    @Test
//    @DisplayName("checkEventsParams: user not logged in")
//    public void checkEventsParamsTest6() {
//        new UserController().logOut();
//        assertEquals(Keywords.unLogin, controller.checkEventsParams(
//                "event",
//                "2020-11-26 20:00",
//                "place",
//                "description"
//        ));
//        new UserController().logIn(user.getName(), "pass");
//    }
//
//    @Test
//    @DisplayName("create: event created")
//    public void createTest1() {
//        String result = controller.create("event1", "2020-11-26 20:00", "place", "description");
//        assertEquals("event event1 added", result);
//    }
//
//    @Test
//    @DisplayName("update: event updated")
//    public void updateTest1() {
//        String result = controller.update(event.getId().toString(), event.getName(),
//                "2020-11-26 20:00", event.getPlace(), event.getDescription());
//        assertEquals("event event updated", result);
//    }
//
//    @Test
//    @DisplayName("update: id is not a number")
//    public void updateTest2() {
//        String result = controller.update("id", event.getName(),
//                "2020-11-26 20:00", event.getPlace(), event.getDescription());
//        assertEquals("Id is not a number", result);
//    }
//
//    @Test
//    @DisplayName("remove: event removed")
//    public void removeTest1() {
//        tEvent = new Event("temp", "place",
//                LocalDateTime.now(), Category.Прогулка, "description");
//        try (Session session = TestSessionFactory.getInstance().openSession()) {
//            session.getTransaction().begin();
//            session.refresh(user);
//            user.addCreatedEvent(tEvent);
//            session.save(tEvent);
//            session.getTransaction().commit();
//        }
//        String result = controller.remove(tEvent.getId().toString());
//        assertEquals("event temp removed", result);
//    }
//
//    @Test
//    @DisplayName("remove: id is not a number")
//    public void removeTest2() {
//        String result = controller.remove("id");
//        assertEquals("Id is not a number", result);
//    }
//
//    @Test
//    @DisplayName("find: events found")
//    public void findTest1() {
//        EventQuery query = new EventQuery();
//        query.setName(event.getName());
//        query.setPlace(event.getPlace());
//        query.setTime(event.getTime().toLocalDate().toString());
//        query.setCategory(event.getCategory().toString());
//        String result = controller.find(query);
//        assertNotNull(result);
//        assertNotEquals(Keywords.exception, result);
//    }
//
//    @Test
//    @DisplayName("findByName: event found")
//    public void findByNameTest1() {
//        String result = controller.findByName(event.getName());
//        assertEquals("found event "+event.getId()+"\nevent\nplace\n" +
//                event.getTime().toLocalDate().toString()+ " " +
//                event.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
//                "\ndescription",
//                result);
//        System.out.println(result);
//    }
//
//    @Test
//    @DisplayName("findByName: event not found")
//    public void findByNameTest2() {
//        String result = controller.findByName("");
//        assertEquals(Keywords.exception, result);
//    }
}
