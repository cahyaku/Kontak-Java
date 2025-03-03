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
            System.out.println("Error while reading your input, please try again");
            return -1;
        }

        //        try {
        //            String input = readLineAsString(message, null);
        //            return Integer.parseInt(input);
        //        } catch (Exception e) {
        //            System.out.println("Error while reading your input...");
        //            return -1;
        //        }
    }


    public static String readLineAsString(String message, String required) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        // Ini saat edit, karena boleh tidak mengubah nama
        if (required == null || required.isEmpty()) {
            System.out.print(message);
            input = scanner.nextLine();
        } else {
            //Saat create data inputan wajib ada tidak boleh string kosong.
            while (true) {
                System.out.print(message);
                input = scanner.nextLine().trim(); // Fungsi trim adalah untuk menghapus spasi ekstra

                if (!input.isEmpty()) {
                    // System.out.println("Anda memasukkan: " + input);
                    break; // Keluar dari loop jika input valid
                } else {
                    System.out.println("input should not be empty, please try again!");
                }
            }
        }
        return input;
    }

    public static void pressAnyKeyToContinue() {
        System.out.println("Press any key to continue...");
        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();
    }
}