import database.utils.HibernateSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.apache.logging.log4j.Logger;
import view.TelegramBot;

/**
 * Входная точка приложения
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        HibernateSessionFactory.getSessionFactory();
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        logger.info("Приложение запущено");
        try {
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
