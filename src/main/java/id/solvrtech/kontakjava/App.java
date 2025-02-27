package id.solvrtech.kontakjava;

import id.solvrtech.kontakjava.entity.Person;
import id.solvrtech.kontakjava.repository.InMemoryPersonRepository;
import id.solvrtech.kontakjava.repository.PersonRepository;
import id.solvrtech.kontakjava.service.PersonService;

import java.util.List;
import java.util.Scanner;

import static id.solvrtech.kontakjava.helper.TerminalHelper.pressAnyKeyToContinue;
import static id.solvrtech.kontakjava.helper.TerminalHelper.readLineAsInt;
import static java.sql.DriverManager.println;

public class App {

    public static void run() {
        PersonRepository repository = new InMemoryPersonRepository();
        PersonService personService = new PersonService(repository);

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("----------- KontakJava V 1 (In Memory) ----------");
            System.out.println("1. Show all persons");
            System.out.println("2. Create new person");
            System.out.println("3. Edit person");
            System.out.println("4. Delete person");
            System.out.println("5. Exit");
            choice = readLineAsInt("Choose an option: ");
            println("-------------------------------------------------");

            switch (choice) {
                case 1:
                    System.out.println("All persons: ");
                    List<Person> persons = personService.getAllPersons();
                    for (Person person : persons) {

                    // Jika seperti ini hanya akan menampilkan kode aneh, mungkin itu kode objeknya
//                  // Untuk mendapatkan namenya maka maka di perlukan mengakases method getternya.
//                        System.out.println(person);
                        System.out.println(person.getName());
                    }
//                    scanner.nextLine();
                    pressAnyKeyToContinue();
                    continue;
                case 2:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    int phone = readLineAsInt("Enter phone number: ");
                    personService.createPerson(name, phone);
                    pressAnyKeyToContinue();
                    continue;
                case 3:
                    System.out.println("Enter ID of person to edit: ");
                    pressAnyKeyToContinue();
                    continue;
                case 4:
                    System.out.println("Enter ID of person to delete: ");
                    pressAnyKeyToContinue();
                    continue;
                case 5:
                    System.out.println("Exiting... bye bye 👋😃");
                    pressAnyKeyToContinue();
                    break;
                default:
                    System.out.println("Invalid input, please try again :) ");
            }
        } while (choice != 5);
        //        scanner.close();
    }
}