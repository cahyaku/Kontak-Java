package id.solvrtech.kontakjava.repository;

import id.solvrtech.kontakjava.entity.Person;

import java.util.List;

public class MysqlPersonRepository implements PersonRepository {

    @Override
    public List<Person> getAll() {
        return List.of();
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
        return null;
    }

    @Override
    public Person update(int id, Person person) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public boolean isPhoneNumberExists(Integer id, String phoneNumber) {
        return false;
    }
}
