package id.solvrtech.kontakjava.repository;
import id.solvrtech.kontakjava.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPersonRepository implements PersonRepository {
    // Ini untuk append data ke dalam List objek person.
    private List<Person> persons = new ArrayList<>();

    /**
     * Kelas yang mengimplementasikan interface wajib meng-override atau mengimplementasikan semua methodnya juga.
     * Karena semua method yang ada di dalam interface bersifat abstrak (method abstrak tidak memiliki body).
     */
    @Override
    public List<Person> getAll() {
        return List.of(persons.toArray(new Person[0]));
    }

    @Override
    public Person getById(int id) {
        return null;
    }

    @Override
    public List<Person> getByName(String name) {
        return List.of();
    }

    @Override
    public List<Person> getByPhone(String phone) {
        return List.of();
    }

    @Override
    public Person create(Person person) {
        // ini untuk menambahkan objek person kedalam list
        persons.add(person);
        return person;
    }

    @Override
    public Person update(Person person) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

//    public List<Person> getPersons() {
//        return persons;
//    }
//
//    public void setPersons(List<Person> persons) {
//        this.persons = persons;
//    }
}
