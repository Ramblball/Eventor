package dbTests.services;

import database.model.Category;
import database.model.Event;
import database.model.User;
import database.services.EventService;
import database.utils.EventQuery;
import dbTests.TestSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventServiceTest {
    EventService service;
    User user;
    Event event;
    Event tEvent;
    User tUser;

    @BeforeAll
    public void beforeAll() {
        service = new EventService();
        user = new User();
        user.setName("user");
        user.setPassword("pass");
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
    @DisplayName("create: event created")
    public void createTest1() {
        tEvent = new Event("event1", "place",
                LocalDateTime.now(), Category.Прогулка, "description");
        boolean result = service.create(user, tEvent);
        assertTrue(result);
        try (Session session = TestSessionFactory.getInstance().openSession()) {
            session.refresh(user);
            user.removeCreatedEvent(tEvent);
        }
        try (Session session = TestSessionFactory.getInstance().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(tEvent);
            transaction.commit();
        }
        tEvent = null;
    }

    @Test
    @DisplayName("create: user is not exist")
    public void createTest3() {
        tUser = new User();
        user.setName("name1");
        user.setPassword("pass");
        tEvent = new Event("event1", "place",
                LocalDateTime.now(), Category.Прогулка, "description");
        boolean result = service.create(tUser, tEvent);
        assertFalse(result);
        tEvent = null;
        tUser = null;
    }

    @Test
    @DisplayName("update: event updated")
    public void updateTest1() {
        event.setTime(LocalDateTime.now());
        boolean result = service.update(event);
        assertTrue(result);
    }

    @Test
    @DisplayName("update: event is not exits")
    public void updateTest2() {
        tEvent = new Event("event1", "place",
                LocalDateTime.now(), Category.Прогулка, "description");
        boolean result = service.update(tEvent);
        assertFalse(result);
    }

    @Test
    @DisplayName("remove: event removed")
    public void removeTest1() {
        tEvent = new Event("event1", "place",
                LocalDateTime.now(), Category.Прогулка, "description");
        try (Session session = TestSessionFactory.getInstance().openSession()) {
            session.getTransaction().begin();
            session.refresh(user);
            user.addCreatedEvent(tEvent);
            session.save(tEvent);
            session.getTransaction().commit();
        }
        boolean result = service.remove(user, tEvent);
        assertTrue(result);
    }

//    @Test
//    @DisplayName("remove: event is not exist")
//    public void removeTest2() {
//        tEvent = new Event("event1", "place",
//                LocalDateTime.now(), Category.Прогулка, "description");
//        boolean result = service.remove(user, tEvent);
//        assertFalse(result);
//    }
//
//    @Test
//    @DisplayName("remove: user is not exist")
//    public void removeTest3() {
//        tUser = new User();
//        user.setName("name1");
//        user.setPassword("pass");
//        boolean result = service.remove(user, event);
//        assertFalse(result);
//    }

    @Test
    @DisplayName("findById: event found")
    public void findByIdTest1() {
        Event result = service.findById(event.getId());
        assertNotNull(result);
        assertEquals(event, result);
    }

    @Test
    @DisplayName("findById: event not found")
    public void findByIdTest2() {
        Event result = service.findById(-1);
        assertNull(result);
    }

    @Test
    @DisplayName("findByName: event found")
    public void findByNameTest1() {
        Event result = service.findByName(event.getName());
        assertNotNull(result);
        assertEquals(event, result);
    }

    @Test
    @DisplayName("findByName: event not found")
    public void findByNameTest2() {
        Event result = service.findByName("");
        assertNull(result);
    }

    @Test
    @DisplayName("findAll: events found")
    public void findAllTest1() {
        List<Event> result = service.findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("find: events found")
    public void findTest1() {
        EventQuery query = new EventQuery();
        query.setName(event.getName());
        query.setPlace(event.getPlace());
        query.setTime(event.getTime().toLocalDate().toString());
        query.setCategory(event.getCategory().toString());
        List<Event> result = service.find(query);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(event, result.get(0));
    }
}
