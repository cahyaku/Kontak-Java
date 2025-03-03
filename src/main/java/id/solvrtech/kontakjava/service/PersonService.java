package id.solvrtech.kontakjava.service;

import id.solvrtech.kontakjava.entity.Person;
import id.solvrtech.kontakjava.repository.InMemoryPersonRepository;
import id.solvrtech.kontakjava.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static id.solvrtech.kontakjava.helper.Helper.readLineAsInt;

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

    public void createPerson(String name, int phone) {
        Person person = new Person(name, phone);
        personRepository.create(person);
    }

    public Person updatePerson(Person person, String name, String phone) {
//        if (phone.equals("")) {
//            person.setPhone(person.getPhone());
//        } else {
//            person.setPhone(Integer.parseInt(phone));
//        }
//
//        if (name.equals("")) {
//            person.setName(person.getName());
//        } else {
//            person.setName(name);
//        }

        person.setName(phone.equals("") ? person.getName() : name);
        person.setPhone(phone.equals("") ? person.getPhone() : Integer.parseInt(phone));

//        person.setName(name);
//        person.setPhone(phone);
        return personRepository.update(person.getId(), person);
    }

    public void deletePerson(Person person) {
        personRepository.deleteById(person.getId());
    }

    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

    /**
     * Buat method untuk search, agar bisa melakukan pencarian berdasarkan nama ataupun phone number.
     */

    /**
     * Buat validasi untuk name number saat create/edit new person data
     */

    /**
     * Validasi untuk mengecek apakah phone number sudah ada yang pakai tidak.
     * Semestinya bisa satu kali pengecekan, ketika melakukan edit dan create.
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

