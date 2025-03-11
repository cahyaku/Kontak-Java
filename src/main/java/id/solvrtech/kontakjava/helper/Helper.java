package id.solvrtech.kontakjava.helper;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Helper {
    public static Integer readLineAsInt(String message, String errorMessage) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print(message);
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (Exception e) {
            if (errorMessage != null) {
                System.out.println(errorMessage);
            } else {
                System.out.println("Error while reading your input, please try again");
            }
            return -1;
        }
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


//    public static boolean isNumeric(String str) {
//        try {
//            Integer.parseInt(str);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }

    /**
     * Tanda ^ : untuk menandai string di awal
     * 0[0-9]{1,15} : Dimulai dari nol diikuti oleh 1 sampai 15 angka, sehinnga total panjang hingga 16.
     * \\+62[0-9]{1,14} : Dimulai dengan tanda +62 yang diikuti 1 samapai 14 angka, sehingga total panjang 16.
     **/
    private static final String PHONE_REGEX = "^(0[0-9]{1,15}|\\+62[0-9]{1,14})$";

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return Pattern.matches(PHONE_REGEX, phoneNumber.trim());
    }

    public static boolean confirmYesNo() {
        while (true) {
            String confirm = readLineAsString("Do you want to confirm your selection? [Y/N]:", null);
            if (confirm.equalsIgnoreCase("Y")) {
                return true;
            } else if (confirm.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Confirm your selection, please input Y or N");
            }
        }
    }
}