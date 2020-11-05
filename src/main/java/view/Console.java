package view;

import controller.Controller;

import java.util.Scanner;

public class Console {
    private final Controller controller = new Controller();

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            var input = scanner.nextLine().toLowerCase().split(" ");
            switch (input[0]) {
                case "create":
                    if (input[1].equals("event")) {
                        System.out.println(controller.createEvent(input[2], input[3], input[4]));
                    } else {
                        System.out.println(controller.createUser(input[2], input[3]));
                    }
                    break;
                case "login":
                    System.out.println(controller.logIn(input[1], input[2]));
                    break;
                case "help":
                    System.out.println(controller.getHelp());
                    break;
                case "find":
                    System.out.println(controller.findEvent(input[1]));
                    break;
                case "signup":
                    System.out.println(controller.signUp(input[1]));
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