package viewTests;

import controller.Keywords;
import org.junit.jupiter.api.Test;
import view.Chat;

import static org.junit.jupiter.api.Assertions.*;

public class ChatTests {
    public static Chat chat = new Chat();

    @Test
    public void createUser() {
        var result = chat.execute("create user chatTest chatTest");
        assertEquals("user chatTest added", result);
    }

    @Test
    public void createEvent() {
        var result = chat.execute("create event chatEventTest chatTestFactory chatTestDescription");
        assertEquals("event chatEventTest added", result);
    }

    @Test
    public void logIn() {
        var result = chat.execute("test2 qwerty");
        assertEquals("Welcome, test2", result);
    }

    @Test
    public void getHelp() {
        var result = chat.execute("help");
        assertEquals(Keywords.help, result);
    }

    @Test
    public void findEvent() {
        var result = chat.execute("find chatEventTest");
        assertTrue(result.contains("chatEventTest") && result.contains("chatTestFactory")
                && result.contains("chatTestDescription"));
    }

    @Test
    public void signUp() {
        chat.execute("login test2 qwerty");
        var result = chat.execute("signup chatEventTest");
        assertEquals("test2 signed up for chatEventTest", result);
    }

    @Test
    public void unknownCommand() {
        var result = chat.execute("please sign me for chatTestEvent");
        assertEquals("Unknown command. Try to type \"help\"", result);
    }
}
