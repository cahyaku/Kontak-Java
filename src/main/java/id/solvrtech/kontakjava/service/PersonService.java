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
     *
     * @param name
     */
    public void createPerson(String name, int phone) {
        Person person = new Person(name, phone);

        // Ini harus memanggil parameter yang ada di constructor,
        // karena method create dalam Interface PersonRepository tidak di-set menjadi static
        personRepository.create(person);
//        return PersonRepository.create(person);
    }

    //    public Person updatePerson(Person person)
    public Person updatePerson(int id, String name, int phone) {
        // ini untuk cek input person, apakah id-nya sama dan benar' ada maka lanjutkan dengan mengedit data.
        Person person = personRepository.getById(id);
//        if (person != null) {
        person.setName(name);
        person.setPhone(phone);
//        }
        return personRepository.update(person);
    }

    public Person DeletePerson(int id) {
        Person person = personRepository.getById(id);
        if (person != null) {
            personRepository.deleteById(person.getId());
        }
//        return null;
        return person;
    }

    // Untuk mendapatkan semua persons
    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

}

