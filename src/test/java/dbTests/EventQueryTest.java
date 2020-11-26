package dbTests;

import database.model.Event;
import database.services.EventService;
import database.utils.EventQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventQueryTest {

    @Test
    @DisplayName("correctly execute with params")
    public void validTest1() {
        EventQuery query = new EventQuery();
        query.setName("event");
        query.setPlace("Pushkin st.");
        query.setDescription("Very long and interesting description");
        query.setCategory("1");

        assertEquals("SELECT eventor_schema.event.* " +
                        "FROM eventor_schema.event " +
                        "LEFT JOIN eventor_schema.event_vector ev ON event.id = ev.event_id " +
                        "WHERE name LIKE '%event%' " +
                        "AND place LIKE '%Pushkin st.%' " +
                        "AND event_description @@ plainto_tsquery('Very<->long<->and<->interesting<->description') " +
                        "AND category = 1 " +
                        "ORDER BY time",
                query.execute());
    }

    @Test
    @DisplayName("correctly finds events")
    public void validTest2() {
        EventService service = new EventService();
        EventQuery query = new EventQuery();
        Event event = service.findAll().get(0);
        query.setName(event.getName());
        query.setCategory(event.getCategory().toString());
        query.setPlace(event.getPlace());
        query.setDescription(event.getDescription());
        query.setTime(event.getTime().toLocalDate().toString());

        List<Event> list = service.find(query);
        assertNotNull(list);
        assertFalse(list.isEmpty());

        Event result = list.get(0);

        assertEquals(event, result);
    }

    @Test
    @DisplayName("execute query without params")
    public void invalidTest1() {
        EventQuery query = new EventQuery();
        NullPointerException exception = null;

        assertTrue(query.isEmpty());

        try {
            query.execute();
        } catch (NullPointerException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @Test
    @DisplayName("execute with incorrect category param")
    public void invalidTest2() {
        EventService service = new EventService();
        EventQuery query = new EventQuery();
        query.setCategory("text");

        List<Event> list = service.find(query);

        assertNull(list);
    }
}
