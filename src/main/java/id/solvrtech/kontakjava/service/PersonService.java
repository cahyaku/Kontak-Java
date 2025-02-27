package id.solvrtech.kontakjava.service;
import id.solvrtech.kontakjava.entity.Person;
import id.solvrtech.kontakjava.repository.PersonRepository;

import java.util.List;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Entah bagaimana ini masih mencoba saja.
     * @param name
     * @return
     */
    public Person createPerson(String name, int phone) {
        Person person = new Person(name, phone);
        // Ini harus memanggil parameter yang ada di constructor,
        // karena method create dalam Interface PersonRepository tidak di-set menjadi static
        return personRepository.create(person);
//        return PersonRepository.create(person);
    }

    public Person updatePerson(Person person) {
        return null;
    }

    public Person DeletePerson(int id) {
        return null;
    }


    // Untuk mendapatkan semua persons
    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }
}

