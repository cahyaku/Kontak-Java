package id.solvrtech.kontakjava.repository;

import id.solvrtech.kontakjava.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryPersonRepository implements PersonRepository {
    // Ini untuk append data ke dalam List objek person.
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

        // Stream() adalah fungsi yang digunakan untuk melakukan operasi seperti pemetaan, penyaringan,
        // dan pengurangan pada kumpulan data.
//        return persons.stream()
//                .filter(person -> person.getId() == id)
//                .findFirst()
//                .orElse(null);
    }

    @Override
    public List<Person> getByName(String name) {
        // 1. Menggunakan fungsi toList(), karena jumlah data yang ditemukan bisa lebih dari satu.
        // 2. Fungsi method equalsIgnoreCase(), digunakan untuk mengabaikan lowercase atau pun uppercase.
        // 3. Fungsi method contains() adalah untuk mencari kata dalam daftar atau url
        // 4. Sedangkan equals() digunakan untuk membandingkan objek berdasarkan implementasi method (membandingkan konten objek).
//        return persons.stream().filter(person -> person.getName().equalsIgnoreCase(name)).toList();
//        return persons.stream().filter(person -> person.getName().equalsIgnoreCase(name))collect(Collectors.toList());

        return persons.stream()
                .filter(person -> person.getName().contains(name))
                .collect(Collectors.toList());

        // return persons.stream().filter(person -> person.getName() == name).findFirst().orElse(null);
    }

    @Override
    public List<Person> getByPhone(String phone) {
        return persons.stream()
                .filter(person -> person.getPhone().contains(phone))
                .collect(Collectors.toList());

//        return persons.stream().filter(person -> person.getPhone().equals(phone)).collect(Collectors.toList());
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
}
