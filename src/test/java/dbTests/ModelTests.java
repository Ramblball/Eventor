package dbTests;


import database.utils.HibernateSessionFactory;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hibernate.testing.transaction.TransactionUtil.*;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class ModelTests {
    SessionFactory factory = TestSessionFactory.getInstance();

    @Test
    public void test() {
    }
}
