package view;

import org.telegram.telegrambots.meta.api.objects.User;
import java.util.HashMap;

/**
 * Класс для хранения состояний пользователя
 */
public class UserStateCache {

    private static final HashMap<User, Progress> map = new HashMap<>();

    public static Progress getProgress(User user) {
        return map.get(user);
    }

    public static void setProgress(User user, Progress progress) {
        map.put(user, progress);
    }
}

