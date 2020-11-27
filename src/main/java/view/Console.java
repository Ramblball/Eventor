package view;

import java.util.Scanner;

public class Console {
    public void onUpdateReceived() {
        var scanner = new Scanner(System.in);
        while (true) {
            var input = scanner.nextLine();
            var provider = new Provider(input);
            System.out.println(provider.execute());
        }
    }
}