import database.tasks.EventRemoveTask;
import database.utils.HibernateSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.apache.logging.log4j.Logger;
import view.TelegramBot;
import view.commands.Command;
import view.dialog.DefaultDialog;
import view.dialog.Dialog;

import java.util.Timer;

/**
 * Входная точка приложения
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final EventRemoveTask task = new EventRemoveTask();
    private static final Timer timer = new Timer();

    public static void main(String[] args) {
        init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot());
            logger.info("Приложение запущено");
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.error("Ошибка инициализации бота", e);
        }
    }

    private static void init() {
        HibernateSessionFactory.getSessionFactory();
        timer.scheduleAtFixedRate(task, 0, 60 * 60 * 1000);
        Dialog dialog = Dialog.Unknown;
        DefaultDialog defaultDialog = DefaultDialog.Unknown;
        Command command = Command.Unknown;
        ApiContextInitializer.init();
    }
}
