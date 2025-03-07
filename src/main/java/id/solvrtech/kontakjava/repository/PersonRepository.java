package id.solvrtech.kontakjava.repository;

import id.solvrtech.kontakjava.entity.Person;

import java.sql.SQLException;
import java.util.List;

public interface PersonRepository {
    List<Person> getAll() throws SQLException;

    Person getById(int id);

    List<Person> getByName(String name);

    List<Person> getByPhone(String phone);

    Person create(Person person);

    Person update(int id, Person person);

    void deleteById(int id);

    boolean isPhoneNumberExists(Integer id, String phoneNumber);
}
