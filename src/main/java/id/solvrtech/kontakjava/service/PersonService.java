package id.solvrtech.kontakjava.service;

import id.solvrtech.kontakjava.entity.Person;
import id.solvrtech.kontakjava.repository.InMemoryPersonRepository;
import id.solvrtech.kontakjava.repository.PersonRepository;

import java.util.*;

public class PersonService {
    private PersonRepository personRepository;

    public PersonService() {
        this.personRepository = new InMemoryPersonRepository();
    }

    public ArrayList<Person> getAll() {
        return (ArrayList<Person>) personRepository.getAll();
    }

    public Person getPersonById(int id) {
        return personRepository.getById(id);
    }

    public void createPerson(String name, String phone) {
        Person person = new Person(name, phone);
        personRepository.create(person);
    }

    public Person updatePerson(Person updatePerson, String name, String phone) {
        updatePerson.setName(name.equals("") ? updatePerson.getName() : name);
        updatePerson.setPhone(phone.equals("") ? updatePerson.getPhone() : phone);
        return personRepository.update(updatePerson.getId(), updatePerson);
    }

    public void deletePerson(Person person) {
        personRepository.deleteById(person.getId());
    }

    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

    public ArrayList<Person> searchPerson(String searchInput) {
        List<Person> personByName = personRepository.getByName(searchInput);
        List<Person> personByPhone = personRepository.getByPhone(searchInput);
        if (!personByName.isEmpty()) {
            return (ArrayList<Person>) personByName;
        } else if (!personByPhone.isEmpty()) {
            return (ArrayList<Person>) personByPhone;
        }
        return null;
    }
}

