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
            System.out.println("2. Create new person");
            System.out.println("3. Edit person");
            System.out.println("4. Delete person");
            System.out.println("5. Exit");
            choice = readLineAsInt("Choose an option: ", null);
            System.out.println(" ");

            switch (choice) {
                case 1:
                    System.out.println("======== All persons data ========");
                    showPersonsData(allPersons);
                    pressAnyKeyToContinue();
                    continue;

                case 2:
                    System.out.println("======== Create new person ========");
                    String name = readLineAsString("Enter name: ", "required");
                    while (true) {
                        String phone = readLineAsString("Enter phone number:", null);
                        if (isNumeric(phone)) {
                            personService.createPerson(name, Integer.parseInt(phone));
                            break;
                        } else {
                            System.out.println("Please enter a valid phone number");
                        }
                    }
                    pressAnyKeyToContinue();
                    continue;

                case 3:
                    if (!allPersons.isEmpty()) {
                        System.out.println("======== Edit person ========");
                        // ini untuk cek input person, apakah id-nya sama dan benar' ada maka lanjutkan dengan mengedit data.
                        int id = readLineAsInt("Enter ID of person to edit: ", null);
                        Person personToUpdate = personService.getPersonById(id);
                        if (personToUpdate != null) {
                            System.out.println("--- Previous Data ---");
                            System.out.println("Name: " + personToUpdate.getName());
                            System.out.println("Phone number: " + personToUpdate.getPhone());
                            System.out.println("----------------------");

                            String updateName = readLineAsString("Enter name:", null);
                            String updatePhone = readLineAsString("Enter phone number:", null);
                            personService.updatePerson(personToUpdate, updateName, updatePhone);
                        } else {
                            System.out.println("Person not found...");
                        }
                    } else {
                        System.out.println("Empty data!");
                    }
                    pressAnyKeyToContinue();
                    continue;

                case 4:
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

                case 5:
                    System.out.println("Exiting,bye!");
                    pressAnyKeyToContinue();
                    break;

                default:
                    System.out.println("Select a valid option (1, 2, 3, 4, or 5)! ");
            }
        } while (choice != 5);
    }
}