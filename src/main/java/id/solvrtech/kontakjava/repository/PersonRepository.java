package id.solvrtech.kontakjava.repository;

import id.solvrtech.kontakjava.entity.Person;

import java.util.List;

public interface PersonRepository {
    List<Person> getAll();

    Person getById(int id);

    List<Person> getByName(String name);

    List<Person> getByPhone(String phone);

    Person create(Person person);

    //    Person update(int id, Person person);
    Person update(int id, Person person);

    void deleteById(int id);
}
