import database.model.User;
import database.services.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = new User("name", "pass");
        userService.saveUser(user);
    }
}
