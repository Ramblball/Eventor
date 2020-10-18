package view;

import controller.Controller;

import java.io.IOException;
import java.util.Scanner;

public class Console {
    private static Controller controller = new Controller();

    public void execute() {
        String result = null;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                result = controller.execute(scanner.nextLine());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(result);
        }
    }
}