package id.solvrtech.kontakjava.helper;

import java.util.Scanner;
public class TerminalHelper {

    public static Integer readLineAsInt(String message) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print(message);
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Error while reading your input...");
            return -1;
        }
    }

    public static void pressAnyKeyToContinue() {
        System.out.println("Press any key to continue...");
        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();
    }
}