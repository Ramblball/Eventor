import database.utils.HibernateSessionFactory;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import view.Console;
import view.TelegramBot;

/**
 * Входная точка приложения
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();
        HibernateSessionFactory.getSessionFactory();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        logger.info("Приложение запущено");
        try {
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
//        var console = new Console();
//        console.onUpdateReceived();
    }
}
