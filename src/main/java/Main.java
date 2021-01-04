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

/**
 * Входная точка приложения
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final EventRemoveTask databaseCleaner = new EventRemoveTask();

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

    /**
     * Метод для инициализации контекста приложения
     *  Подключение базы данных
     *  Задача удаления прошедших мероприятий
     *  Команд и диалогов
     *  Контекст бота
     */
    private static void init() {
        HibernateSessionFactory.getSessionFactory();
        databaseCleaner.init();
        Dialog dialog = Dialog.Unknown;
        DefaultDialog defaultDialog = DefaultDialog.Unknown;
        Command command = Command.Unknown;
        ApiContextInitializer.init();
    }
}
