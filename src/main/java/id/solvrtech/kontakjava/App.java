package id.solvrtech.kontakjava;

import id.solvrtech.kontakjava.entity.Person;
import id.solvrtech.kontakjava.repository.InMemoryPersonRepository;
import id.solvrtech.kontakjava.repository.PersonRepository;
import id.solvrtech.kontakjava.service.PersonService;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static id.solvrtech.kontakjava.helper.TerminalHelper.*;

public class App {
    public static void run() {

        PersonRepository repository = new InMemoryPersonRepository();
        PersonService personService = new PersonService(repository);

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("*********** \uD81A\uDE06\uD81A\uDE06 KontakJava V1 (In Memory) \uD81A\uDE06\uD81A\uDE06 ***********");

            System.out.println("1. Show all persons");
            System.out.println("2. Create new person");
            System.out.println("3. Edit person");
            System.out.println("4. Delete person");
            System.out.println("5. Exit");
            choice = readLineAsInt("Choose an option: ");
            System.out.println(" ");

            switch (choice) {
                case 1:
                    System.out.println("======== All persons data ========");
                    personService.showPersons();
                    pressAnyKeyToContinue();
                    continue;

                case 2:
                    System.out.println("======== Create new person ========");
                    personService.createPerson();
                    pressAnyKeyToContinue();
                    continue;

                case 3:
                    System.out.println("======== Edit person ========");
                    int id = readLineAsInt("Enter ID of person to edit: ");
                    String updateName = readLineAsString("Enter name:", null);
                    int updatePhone = readLineAsInt("Enter phone number:");
                    personService.updatePerson(id, updateName, updatePhone);
                    pressAnyKeyToContinue();
                    continue;

                case 4:
                    System.out.println("======== Delete person ========");
                    int personId = readLineAsInt("Enter ID of person to delete: ");
                    personService.DeletePerson(personId);
                    pressAnyKeyToContinue();
                    continue;

                case 5:
                    System.out.println("Exiting,bye!");
                    pressAnyKeyToContinue();
                    break;

                default:
                    System.out.println("Invalid input, please try again :) ");
            }
        } while (choice != 5);
        //        scanner.close();
    }
}