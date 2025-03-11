package id.solvrtech.kontakjava.repository;

import id.solvrtech.kontakjava.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InMemoryPersonRepository implements PersonRepository {

    private List<Person> persons = new ArrayList<>();

    /**
     * Kelas yang mengimplementasikan interface wajib meng-override atau mengimplementasikan semua methodnya juga.
     * Karena semua method yang ada di dalam interface bersifat abstrak (method abstrak tidak memiliki body).
     */
    @Override
    public List<Person> getAll() {
        return this.persons;
    }

    @Override
    public Person getById(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    @Override
    public List<Person> getByName(String name) {
        return persons.stream().filter(person -> person.getName().contains(name)).collect(Collectors.toList());
    }

    @Override
    public List<Person> getByPhone(String phone) {
        return persons.stream()
                .filter(person -> person.getPhone().contains(phone))
                .collect(Collectors.toList());
    }

    @Override
    public Person create(Person person) {
        // ini untuk mendapatkan id persons
        if (persons.isEmpty()) {
            person.setId(0);
        } else {
            person.setId(persons.get(persons.size() - 1).getId() + 1);
        }

        persons.add(person);
        return person;
    }

    @Override
    public Person update(int id, Person person) {
        for (Person p : persons) {
            if (p.getId() == id) {
                p.setName(person.getName());
                p.setPhone(person.getPhone());
                return p;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        persons.removeIf(person -> person.getId() == id);
    }

    @Override
    public boolean doesPhoneNumberExists(Integer id, String phoneNumber) {
        for (Person person : persons) {
            boolean samePhoneNumber = Objects.equals(person.getPhone(), phoneNumber);
            boolean sameId = Objects.equals(person.getId(), id);
            if (id == null) { // saat id null hanya cek phone number, karena ini untuk create
                return samePhoneNumber;
            } else {
                return (samePhoneNumber && !sameId);
            }
        }
        return false;
    }
}