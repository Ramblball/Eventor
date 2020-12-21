package database.tasks;

import database.exception.DBException;
import database.services.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class EventRemoveTask extends TimerTask {
    private static final Logger logger = LogManager.getLogger(EventRemoveTask.class);
    private final static EventService service = new EventService();
    private static final Timer timer = new Timer();

    final Runnable remover = () -> {
        try {
            service.removeCompleted();
        } catch (DBException e) {
            logger.error("Ошибка при удалении устаревших мероприятий", e);
        }
    };

    @Override
    public void run() {
        remover.run();
    }
}
