package calculator.util;

import java.util.Scanner;

import calculator.exc.MyException;
import calculator.util.StringWorker;

public class Menu {
    public static void startMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String inputString = scanner.nextLine();
            if ("/help".equals(inputString)) {
                printMenu();
            } else if ("/exit".equals(inputString)) {
                System.out.println("Bye!");
                break;
            } else if (inputString.matches("^/.*")) {
                System.out.println("Unknown command");
            } else if (!(inputString.isEmpty())){
                StringWorker stringWorker = new StringWorker();
                try {
                    stringWorker.stringHandling(inputString);
                } catch (MyException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    static private void printMenu() {
        System.out.print("MENU:\n" +
                "Calculator:\n" +
                "Sums numbers\n" +
                "Multiplies numbers\n" +
                "...\n" +
                "Works with variables\n");
    }
}