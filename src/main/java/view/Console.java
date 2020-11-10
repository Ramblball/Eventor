package view;

import controller.EventController;
import controller.UserController;

import java.util.Scanner;

public class Console {
    private final UserController userController = new UserController();
    private final EventController eventController = new EventController();

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            var input = scanner.nextLine().toLowerCase().split(" ");
            switch (input[0]) {
                case "create":
                    if (input[1].equals("event")) {
                        System.out.println(eventController.create(input[2], input[3], input[4]));
                    } else {
                        System.out.println(userController.create(input[2], input[3]));
                    }
                    break;
                case "login":
                    System.out.println(userController.logIn(input[1], input[2]));
                    break;
                case "help":
                    System.out.println(eventController.getHelp());
                    break;
                case "find":
                    System.out.println(eventController.findEvent(input[1]));
                    break;
                case "signup":
                    System.out.println(userController.signUp(input[1]));
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown command. Try to type \"help\"");
                    break;
            }
        }
    }
}