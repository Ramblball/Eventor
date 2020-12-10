package view;

import org.telegram.telegrambots.meta.api.objects.User;
import java.util.HashMap;

/**
 * Класс для хранения состояний пользователя
 */
public class UserStateCache {

    private static final HashMap<User, Progress> map = new HashMap<>();

    public Progress getProgress(User user) {
        return map.get(user);
    }

    public void setProgress(User user, Progress progress) {
        map.put(user, progress);
    }
}

/**
 * Класс для установки прогресса выполнения текущей команды
 */
class Progress{
    private final Message message;
    // Стадия выполнения запроса
    private final int index;

    Progress(Message message, int count){
        this.message = message;
        this.index = count;
    }

    public Message getMessage() {
        return message;
    }

    public int getIndex() {
        return index;
    }
}