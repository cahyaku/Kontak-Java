package id.solvrtech.kontakjava.service;

import id.solvrtech.kontakjava.entity.Person;
import id.solvrtech.kontakjava.repository.InMemoryPersonRepository;
import id.solvrtech.kontakjava.repository.MySqlPersonRepository;
import id.solvrtech.kontakjava.repository.PersonRepository;

import java.sql.SQLException;
import java.util.*;

public class PersonService {
//    private PersonRepository personRepository;
//
//    public PersonService() {
//        this.personRepository = new InMemoryPersonRepository();
//    }
//
//    public List<Person> getAll() {
//        return personRepository.getAll();
//    }
//
//    public Person getById(int id) {
//        return personRepository.getById(id);
//    }
//
//    public boolean create(String name, String phone) {
//        boolean checkPhoneNumber = personRepository.isPhoneNumberExists(null, phone);
//        if (checkPhoneNumber) {
//            return true;
//        }
//
//        Person person = new Person(name, phone);
//        personRepository.create(person);
//        return false;
//    }
//
//    public boolean update(Person updatePerson, String name, String phone) {
//        boolean checkPhoneNumber = personRepository.isPhoneNumberExists(updatePerson.getId(), phone);
//        if (checkPhoneNumber) {
//            return true;
//        }
//        updatePerson.setName(name.trim().isEmpty() ? updatePerson.getName() : name);
//        updatePerson.setPhone(phone.trim().isEmpty() ? updatePerson.getPhone() : phone);
//
//        personRepository.update(updatePerson.getId(), updatePerson);
//        return false;
//    }
//
//    public void delete(Person person) {
//        personRepository.deleteById(person.getId());
//    }
//
//    public ArrayList<Person> search(String searchInput) {
//        List<Person> personByName = personRepository.getByName(searchInput);
//        List<Person> personByPhone = personRepository.getByPhone(searchInput);
//        if (!personByName.isEmpty()) {
//            return (ArrayList<Person>) personByName;
//        } else if (!personByPhone.isEmpty()) {
//            return (ArrayList<Person>) personByPhone;
//        }
//        return null;
//    }
//
//    public boolean isEmpty() {
//        List<Person> persons = this.getAll();
//        if (persons.isEmpty()) {
//            return true;
//        }
//        return false;
//    }

    private PersonRepository personRepository;

    private MySqlPersonRepository mySqlPersonRepository;

    public PersonService() {
        this.personRepository = new InMemoryPersonRepository();
        this.mySqlPersonRepository = new MySqlPersonRepository();
    }

    public List<Person> getAll() throws SQLException {
        return mySqlPersonRepository.getAll();
    }

    public Person getById(int id) {
        return mySqlPersonRepository.getById(id);
    }

    public boolean create(String name, String phone) {
        boolean checkPhoneNumber = personRepository.isPhoneNumberExists(null, phone);
//        boolean checkPhoneNumber = mySqlPersonRepository.isPhoneNumberExists(null, phone);
        if (checkPhoneNumber) {
            return true;
        }

        Person person = new Person(name, phone);
//        personRepository.create(person);
        mySqlPersonRepository.create(person);
        return false;
    }

    public boolean update(Person updatePerson, String name, String phone) {
        boolean checkPhoneNumber = personRepository.isPhoneNumberExists(updatePerson.getId(), phone);
//        boolean checkPhoneNumber = mySqlPersonRepository.isPhoneNumberExists(updatePerson.getId(), phone);
        if (checkPhoneNumber) {
            return true;
        }
        updatePerson.setName(name.trim().isEmpty() ? updatePerson.getName() : name);
        updatePerson.setPhone(phone.trim().isEmpty() ? updatePerson.getPhone() : phone);

//        personRepository.update(updatePerson.getId(), updatePerson);
        mySqlPersonRepository.update(updatePerson.getId(), updatePerson);
        return false;
    }

    public void delete(Person person) {
//        personRepository.deleteById(person.getId());
        mySqlPersonRepository.deleteById(person.getId());
    }

    public ArrayList<Person> search(String searchInput) {
        List<Person> personByName = personRepository.getByName(searchInput);
        List<Person> personByPhone = personRepository.getByPhone(searchInput);
        if (!personByName.isEmpty()) {
            return (ArrayList<Person>) personByName;
        } else if (!personByPhone.isEmpty()) {
            return (ArrayList<Person>) personByPhone;
        }
        return null;
    }

    public boolean isEmpty() throws SQLException {
        List<Person> persons = this.getAll();
        if (persons.isEmpty()) {
            return true;
        }
        return false;
    }
}
