package id.solvrtech.kontakjava.service;

import id.solvrtech.kontakjava.entity.Person;
import id.solvrtech.kontakjava.repository.PersonRepository;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static id.solvrtech.kontakjava.helper.TerminalHelper.readLineAsInt;
import static id.solvrtech.kontakjava.helper.TerminalHelper.readLineAsString;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void createPerson() {
        String name = readLineAsString("Enter name: ", "required");
        int phone = readLineAsInt("Enter phone number:");
        if (phone != -1) {
            Person person = new Person(name, phone);

            // Ini harus memanggil parameter yang ada di constructor,
            // karena method create dalam Interface PersonRepository tidak di-set menjadi static
            personRepository.create(person);
        } else {
            while (true) {
                phone = readLineAsInt("Enter phone number:");
                if (phone != -1) {
                    Person person = new Person(name, phone);
                    personRepository.create(person);
                    break;
                }
            }
        }
    }

    //    public Person updatePerson(Person person)
    public Person updatePerson(int id, String name, int phone) {
        // ini untuk cek input person, apakah id-nya sama dan benar' ada maka lanjutkan dengan mengedit data.
        Person person = personRepository.getById(id);
        if (person != null) {
            person.setName(name);
            person.setPhone(phone);
        } else {
            System.out.println("Person not found...");
        }
        return personRepository.update(person);
    }

    public Person DeletePerson(int id) {
        Person person = personRepository.getById(id);
        if (person != null) {
            personRepository.deleteById(person.getId());
        } else {
            System.out.println("Person not found...");
        }
        return person;
    }

    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

    public void showPersons() {
        // Ini untuk mendapatkan semua persons, yang akan di-loop dan ditampilkan pada show all persons.
        List<Person> persons = getAllPersons();
        int number = 1;
        if (!persons.isEmpty()) {
            for (Person person : persons) {
                // Jika seperti ini "System.out.println(person);" hanya akan menampilkan kode aneh, mungkin itu kode objeknya
                // Untuk mendapatkan namenya maka maka diperlukan mengakases method getternya.
                System.out.println(number + ".  Name: " + person.getName().toLowerCase(Locale.ROOT));
                System.out.println("    Phone number: " + person.getPhone());
                System.out.println("    Id: " + person.getId());
                number++;
            }
        } else {
            System.out.println("No persons found...");
        }
    }

    public int validatePhoneNumber(String phoneNumber, String condition, int id) {
        int phone = 0;

        List<Person> persons = getAllPersons();

        while (true) {
            phone = readLineAsInt("Enter phone number:");
            if (phone != -1) {
                return phone;
            } else if (phoneNumber.length() != 11) {
                System.out.println("Invalid phone number, wajib 11 characters");
            } else if (condition == "edit") {
                return 1;
            }
        }
    }

    /**
     * Untuk mengecek apakah phone number sudah ada yang pakai tidak.
     *
     * @param phoneNumber
     * @param condition
     * @return
     */
    public boolean isPhoneNumberExits(String phoneNumber, String condition) {
        List<Person> persons = getAllPersons();
        for (Person person : persons) {
            if (condition == "create") {
                if (Objects.equals(person.getPhone(), phoneNumber)) {
                    return true;
                }
                return false;
            } else if (condition == "edit") {
                if (Objects.equals(person.getPhone(), phoneNumber) && !Objects.equals(person.getId(), person.getId())) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}

