//package viewTests;
//
//import controller.Keywords;
//import org.junit.jupiter.api.Test;
//import view.TelegramProvider;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TelegramProviderTests {
//    public static TelegramProvider telegramProvider = new TelegramProvider();
//
//    @Test
//    public void createUser() {
//        var result = telegramProvider.execute("create user chatTest chatTest");
//        assertEquals("user chatTest added", result);
//    }
//
//    @Test
//    public void createEvent() {
//        var result = telegramProvider.execute("create event chatEventTest chatTestFactory chatTestDescription");
//        assertEquals("event chatEventTest added", result);
//    }
//
//    @Test
//    public void logIn() {
//        var result = telegramProvider.execute("test2 qwerty");
//        assertEquals("Welcome, test2", result);
//    }
//
//    @Test
//    public void getHelp() {
//        var result = telegramProvider.execute("help");
//        assertEquals(Keywords.help, result);
//    }
//
//    @Test
//    public void findEvent() {
//        var result = telegramProvider.execute("find chatEventTest");
//        assertTrue(result.contains("chatEventTest") && result.contains("chatTestFactory")
//                && result.contains("chatTestDescription"));
//    }
//
//    @Test
//    public void signUp() {
//        telegramProvider.execute("login test2 qwerty");
//        var result = telegramProvider.execute("signup chatEventTest");
//        assertEquals("test2 signed up for chatEventTest", result);
//    }
//
//    @Test
//    public void unknownCommand() {
//        var result = telegramProvider.execute("please sign me for chatTestEvent");
//        assertEquals("Unknown command. Try to type \"help\"", result);
//    }
//}
