package view;

import java.util.Scanner;

public class Console {
    /**
     * Получает строку с консоли, отдаёт на обработку Провайдеру и отправляет вывод на консоль
     */
    public void onUpdateReceived() {
        var scanner = new Scanner(System.in);
        while (true) {
            var input = scanner.nextLine();
            var provider = new Provider(input);
            System.out.println(provider.execute());
        }
    }
}