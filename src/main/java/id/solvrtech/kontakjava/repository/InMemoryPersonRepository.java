package id.solvrtech.kontakjava.repository;
import id.solvrtech.kontakjava.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryPersonRepository implements PersonRepository {
    // Ini untuk append data ke dalam List objek person.
    private List<Person> persons = new ArrayList<>();

    /**
     * Kelas yang mengimplementasikan interface wajib meng-override atau mengimplementasikan semua methodnya juga.
     * Karena semua method yang ada di dalam interface bersifat abstrak (method abstrak tidak memiliki body).
     */
    @Override
    public List<Person> getAll() {
        return new ArrayList<>(persons);
//        return List.of(persons.toArray(new Person[0]));
    }

    @Override
    public Person getById(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;

        // Stream() adalah fungsi yang digunakan untuk melakukan operasi seperti pemetaan, penyaringan,
        // dan pengurangan pada kumpulan data.

//        persons = persons.stream().filter(person -> person.getId() == id).collect(Collectors.toList());

//        return persons.stream()
//                .filter(person -> person.getId() == id)
//                .findFirst()
//                .orElse(null);
    }

    @Override
    public List<Person> getByName(String name) {
        // ga tau ini untuk apa, baru coba saja.
        persons.stream().filter(person -> person.getName().equals(name)).findFirst().orElse(null);
        return persons;
//        return persons.stream().filter(person -> person.getName() == name).findFirst().orElse(null);
//        return List.of();
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
//        persons
//          if (persons.get(person.getId()) != null) {
//            person.setName(person.getName());
//            person.setPhone(person.getPhone());
//        }
//        return person;
//        person = getById(person.getId());
        person.setName(person.getName());
        person.setPhone(person.getPhone());

//
//        <Person> existingPerson = persons.stream()
//                .filter(p -> p.getId() == person.getId())
//                .findFirst();
//
//        if (existingPerson.isPresent()) {
//            existingPerson.get().setName(person.getName());
//            existingPerson.get().setPhone(person.getPhone());
//            return existingPerson.get();
//        }
//        return null;
        return person;
    }

    @Override
    public void deleteById(int id) {
    persons.removeIf(person -> person.getId() == id);
    }


}
