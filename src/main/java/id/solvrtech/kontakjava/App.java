package id.solvrtech.kontakjava;

import id.solvrtech.kontakjava.entity.Person;
import id.solvrtech.kontakjava.service.PersonService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static id.solvrtech.kontakjava.helper.Helper.*;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        PersonService personService = new PersonService();
        int choice;
        do {
            System.out.println("*********** \uD81A\uDE06\uD81A\uDE06 KontakJava V1 (In Memory) \uD81A\uDE06\uD81A\uDE06 ***********");

            System.out.println("1. Show all persons");
            System.out.println("2. Search person");
            System.out.println("3. Create new person");
            System.out.println("4. Edit person");
            System.out.println("5. Delete person");
            System.out.println("6. Exit");
            choice = readLineAsInt("Choose an option: ", null);
            System.out.println(" ");

            boolean isPersonExists = personService.isEmpty();
            switch (choice) {

                case 1:
                    if (isPersonExists) {
                        System.out.println("People don't exist yet");
                    } else {
                        System.out.println("======== All persons data ========");
                        showPersons(personService.getAll());
                    }
                    pressAnyKeyToContinue();
                    continue;

                case 2:
                    if (!isPersonExists) {
                        System.out.println("======== Search Persons ========");
                        String searchInput = readLineAsString("Enter name or phone number:", "required");
                        ArrayList<Person> searchPerson = personService.search(searchInput);
                        showPersons(searchPerson);
                    } else {
                        System.out.println("Empty search data");
                    }
                    pressAnyKeyToContinue();
                    continue;

                case 3:
                    System.out.println("======== Create new person ========");
                    String name = readLineAsString("Enter name: ", "required");
                    String phone = askForPhoneNumber("required");
                    boolean newPersonCreate = personService.create(name, phone);
                    if (newPersonCreate) {
                        System.out.println("New person could not be created, because phone number is already in use.");
                    } else {
                        System.out.println("New person has been created.");
                    }
                    pressAnyKeyToContinue();
                    continue;

                case 4:
                    if (!isPersonExists) {
                        System.out.println("======== Edit person ========");
                        int id = readLineAsInt("Enter ID of person to edit: ", null);
                        Person personToUpdate = personService.getById(id); // cek id-nya.
                        if (personToUpdate != null) {
                            System.out.println("--- Previous Data ---");
                            System.out.println("Name: " + personToUpdate.getName());
                            System.out.println("Phone number: " + personToUpdate.getPhone());
                            System.out.println("----------------------");

                            String updateName = readLineAsString("Enter name:", null);
                            String updatePhone = askForPhoneNumber(null);
                            boolean updatePerson = personService.update(personToUpdate, updateName, updatePhone);
                            if (updatePerson) {
                                System.out.println("Person data could not be updated, because phone number is already in use.");
                            } else {
                                System.out.println("Person has been updated.");
                            }
                        } else {
                            System.out.println("Person not found...");
                        }
                    } else {
                        System.out.println("Empty data!");
                    }
                    pressAnyKeyToContinue();
                    continue;

                case 5:
                    if (!isPersonExists) {
                        System.out.println("======== Delete person ========");
                        int personId = readLineAsInt("Enter ID of person to delete: ", null);
                        Person personToDelete = personService.getById(personId);

                        if (personToDelete != null) {
                            System.out.println("--- Previous Data ---");
                            System.out.println("Name: " + personToDelete.getName());
                            System.out.println("Phone number: " + personToDelete.getPhone());
                            System.out.println("----------------------");
                            if (confirmYesNo()) {
                                personService.delete(personToDelete);
                                System.out.println("Person has been deleted.");
                            } else {
                                System.out.println("Cancel to delete person.");
                            }
                        } else {
                            System.out.println("Person not found...");
                        }
                    } else {
                        System.out.println("Empty data!");
                    }
                    pressAnyKeyToContinue();
                    continue;

                case 6:
                    System.out.println("Exiting,bye!");
                    pressAnyKeyToContinue();
                    break;

                default:
                    System.out.println("Select a valid option (1, 2, 3, 4, or 5)! ");
            }
        } while (choice != 6);
    }

    public void showPersons(List<Person> persons) {
        int number = 1;
        if (persons == null || persons.isEmpty()) {
            System.out.println("No persons found!");
        } else {
            for (Person person : persons) {
                // Untuk mendapatkan namenya maka diperlukan mengakases method getternya.
                System.out.println(number + ".  Name: " + person.getName().toLowerCase(Locale.ROOT));
                System.out.println("    Phone number: " + person.getPhone());
                System.out.println("    Id: " + person.getId());
                number++;
            }
        }
    }

    /**
     * Parameter condition untuk menentukan apakan wajib isi seperti saat create atau bisa string kosong saat edit.
     */
    public String askForPhoneNumber(String condition) {
        while (true) {
            String phone = readLineAsString("Enter phone number:", condition);

            // Check jika inputan kosong
            if (phone.isEmpty()) {
                return phone; // Kembalikan string kosong jika tidak ada input
            }

            // Periksa apakah phone numbernya numerik
            if (isNumeric(phone)) {
                return phone; // Jika benar numerik kembalikan phone numbernya.
            } else {
                System.out.println("Please enter a valid phone number");
            }
        }
    }
}