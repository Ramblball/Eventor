package database.tasks;

import database.exception.DBException;
import database.services.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Класс для выполнения метода отчистки прошедших мероприятий с периодичностью в 1 час
 */
public class EventRemoveTask {
    private static final Logger logger = LogManager.getLogger(EventRemoveTask.class);
    private final static EventService service = new EventService();

    public void init() {
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

        final Runnable remover = () -> {
            try {
                int count = service.removeCompletedEvents();
                logger.info(String.format("Удалено %d мероприятий", count));
            } catch (DBException e) {
                logger.error("Ошибка при удалении устаревших мероприятий", e);
            }
        };
        
        ScheduledFuture<?> scheduledFuture =
                scheduledExecutor.scheduleAtFixedRate(remover, 0, 60 * 60, TimeUnit.SECONDS);
    }
}
