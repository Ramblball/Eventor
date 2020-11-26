package dbTests;

import database.model.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

//import static org.hibernate.testing.transaction.TransactionUtil.*;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class ModelTests {
    SessionFactory factory = TestSessionFactory.getInstance();
    Session session;

    @BeforeEach
    public void beforeEach() {
        session = factory.openSession();
    }

    @AfterEach
    public void afterEach() {
        session.close();
    }

    @Test
    public void test() {
//        Event event = new Event("");
    }
}
