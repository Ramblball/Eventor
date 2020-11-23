package controllerTests;

import controller.EventController;
import controller.Keywords;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventControllerTests {

    public static EventController eventController = new EventController();

    @Test
    public void getHelp() {
        var result = eventController.getHelp();
        assertEquals(Keywords.help, result);
    }

    @Test
    public void createEvent() {
        var result = eventController.create("dbTestEvent", "TestFactory", "Testing event controller");
        assertEquals("event dbTestEvent added", result);
    }

    @Test
    public void createEventLongName() {
        var result = eventController.create("dbTestEventdbTestEventdbTestEvent", "TestFactory", "Testing event controller");
        assertEquals(Keywords.longName, result);
    }

    @Test
    public void createEventLongPlace() {
        var result = eventController.create("dbTestEvent", "TestFactoryTestFactoryTestFactoryTestFactoryTestFactoryTestFactoryTestFactoryTestFactoryTestFactoryTestFactoryTestFactoryTestFactory", "Testing event controller");
        assertEquals(Keywords.longPlace, result);
    }

    @Test
    public void createEventLongDescription() {
        var result = eventController.create("dbTestEvent", "TestFactory", "Testing event controllerTesting event controllerTesting event controllerTesting event controllerTesting event controllerTesting event controllerTesting event controllerTesting event controllerTesting event controllerTesting event controllerTesting event controllerTesting event controller");
        assertEquals(Keywords.longDesc, result);
    }

    @Test
    public void findEvent() {
        var result = eventController.findEvent("dbTestEvent");
        assertTrue(result.contains("TestFactory") && result.contains("dbTestEvent")
                && result.contains(Keywords.found) && result.contains(Keywords.event)
                && result.contains("Testing event controller"));
    }

    @Test
    public void findNotExistingEvent() {
        var result = eventController.findEvent("$uP6ЯИ/-|\\/\\/\\E");
        assertEquals(Keywords.exception, result);
    }
}
