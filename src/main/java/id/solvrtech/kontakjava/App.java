package id.solvrtech.kontakjava;

import id.solvrtech.kontakjava.entity.Person;
import id.solvrtech.kontakjava.service.PersonService;

import java.util.ArrayList;

import static id.solvrtech.kontakjava.helper.Helper.*;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        PersonService personService = new PersonService();
        ArrayList<Person> allPersons = personService.getAll();

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

            switch (choice) {
                case 1:
                    System.out.println("======== All persons data ========");
                    showPersons(allPersons);
                    pressAnyKeyToContinue();
                    continue;

                case 2:
                    if (!allPersons.isEmpty()) {
                        System.out.println("======== Search Persons ========");
                        String searchInput = readLineAsString("Enter search data, name or phone number:", "required");
                        ArrayList<Person> searchPerson = personService.searchPerson(searchInput);
                        showPersons(searchPerson);
                    } else {
                        System.out.println("Empty search data");
                    }
                    pressAnyKeyToContinue();
                    continue;

                case 3:
                    System.out.println("======== Create new person ========");
                    String name = readLineAsString("Enter name: ", "required");
                    String phone = askForPhoneNumber(allPersons, null);
                    personService.createPerson(name, phone);
                    pressAnyKeyToContinue();
                    continue;

                case 4:
                    if (!allPersons.isEmpty()) {
                        System.out.println("======== Edit person ========");
                        int id = readLineAsInt("Enter ID of person to edit: ", null);
                        Person personToUpdate = personService.getPersonById(id); // cek id-nya.
                        if (personToUpdate != null) {
                            System.out.println("--- Previous Data ---");
                            System.out.println("Name: " + personToUpdate.getName());
                            System.out.println("Phone number: " + personToUpdate.getPhone());
                            System.out.println("----------------------");

                            String updateName = readLineAsString("Enter name:", null);
                            String updatePhone = askForPhoneNumber(allPersons, personToUpdate.getId());
                            personService.updatePerson(personToUpdate, updateName, updatePhone);
                        } else {
                            System.out.println("Person not found...");
                        }
                    } else {
                        System.out.println("Empty data!");
                    }
                    pressAnyKeyToContinue();
                    continue;

                case 5:
                    if (!allPersons.isEmpty()) {
                        System.out.println("======== Delete person ========");
                        int personId = readLineAsInt("Enter ID of person to delete: ", null);
                        Person personToDelete = personService.getPersonById(personId);

                        if (personToDelete != null) {
                            System.out.println("--- Previous Data ---");
                            System.out.println("Name: " + personToDelete.getName());
                            System.out.println("Phone number: " + personToDelete.getPhone());
                            System.out.println("----------------------");
                            if (confirmYesNo()) {
                                personService.deletePerson(personToDelete);
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
}