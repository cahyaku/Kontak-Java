package id.solvrtech.kontakjava.helper;

import id.solvrtech.kontakjava.entity.Person;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Helper {
    public static Integer readLineAsInt(String message, @Nullable String errorMessage) {
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

    public static void showPersons(ArrayList<Person> persons) {
        int number = 1;
        if (persons == null || persons.isEmpty()) {
            System.out.println("No persons found!");
        } else {
            for (Person person : persons) {
                // Jika seperti ini "System.out.println(person);" hanya akan menampilkan kode aneh, mungkin itu kode objeknya
                // Untuk mendapatkan namenya maka maka diperlukan mengakases method getternya.
                System.out.println(number + ".  Name: " + person.getName().toLowerCase(Locale.ROOT));
                System.out.println("    Phone number: " + person.getPhone());
                System.out.println("    Id: " + person.getId());
                number++;
            }
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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

    public static boolean isPhoneNumberExists(List<Person> persons, String phoneNumber, Integer id) {

        for (Person person : persons) {
            boolean samePhoneNumber = Objects.equals(person.getPhone(), phoneNumber);
            boolean sameId = Objects.equals(person.getId(), id);
            if (id == null) { // saat id null hanya cek phone number, karena ini untuk create
                if (samePhoneNumber) {
                    return false;
                }
            } else {
                if (samePhoneNumber && !sameId) { // saat edit cek phone number dengan id-nya, apakan id-nya berbeda
                    return false;
                }
            }
        }
        return true; // Phone number does not exist
    }

    public static String askForPhoneNumber(List<Person> persons, Integer id) {
        while (true) {
            String phone = readLineAsString("Enter phone number:", null);
            if (isNumeric(phone)) {
                boolean createPhoneNumber = isPhoneNumberExists(persons, phone, null);
                boolean updatePhoneNumber = isPhoneNumberExists(persons, phone, id);

                boolean phoneNumber = (id == null) ? createPhoneNumber : updatePhoneNumber;
                if (phoneNumber) {
                    return phone;
                } else {
                    System.out.println("Phone number already exists, please try again");
                }
            } else {
                System.out.println("Please enter a valid phone number");
            }
        }
    }
}