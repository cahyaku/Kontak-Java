package id.solvrtech.kontakjava.service;

import id.solvrtech.kontakjava.entity.Person;
import id.solvrtech.kontakjava.repository.MySqlPersonRepository;
import id.solvrtech.kontakjava.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

public class PersonService {

    private final PersonRepository personRepository;

    public PersonService() {
        // 1. Using person repository (InMemory)
//        this.personRepository = new InMemoryPersonRepository();

        // 2. Using MySql
        this.personRepository = new MySqlPersonRepository();
    }

    public List<Person> getAll() {
        return personRepository.getAll();
    }

    public Person getById(int id) {
        return personRepository.getById(id);
    }

    public boolean create(Person person) {
        boolean checkPhoneNumber = personRepository.doesPhoneNumberExists(null, person.getPhone());
        if (checkPhoneNumber) {
            return true;
        }

        personRepository.create(person);
        return false;
    }

    public boolean update(Person updatePerson, String name, String phone) {
        boolean checkPhoneNumber = personRepository.doesPhoneNumberExists(updatePerson.getId(), phone);
        if (checkPhoneNumber) {
            return true;
        }

        updatePerson.setName(name.trim().isEmpty() ? updatePerson.getName() : name);
        updatePerson.setPhone(phone.trim().isEmpty() ? updatePerson.getPhone() : phone);

        personRepository.update(updatePerson.getId(), updatePerson);
        return false;
    }

    public void delete(Person person) {
        personRepository.deleteById(person.getId());
    }

    public ArrayList<Person> search(String searchInput) {
        List<Person> persons = personRepository.getByNameOrPhone(searchInput);
        if (!persons.isEmpty()) {
            return (ArrayList<Person>) persons;
        }
        return null;
    }

    public boolean isEmpty() {
        List<Person> persons = this.getAll();
        if (persons.isEmpty()) {
            return true;
        }
        return false;
    }
}
