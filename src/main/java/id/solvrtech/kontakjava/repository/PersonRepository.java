package id.solvrtech.kontakjava.repository;
import id.solvrtech.kontakjava.entity.Person;
import java.util.List;

public interface PersonRepository {
    List<Person> getAll();
    Person getById(int id);

    // Dua array list ini fungsinya untuk apa?
    // Untuk search kah? saya belum tahu pasti ini, terus terang saya tidak tahu, saya tidak tau, bahkan saya pun
    // betanya-tanya kenapa saya tidak diberi tahu, sampai sekarang saya tidak tahu :)
    List<Person> getByName(String name);
    List<Person> getByPhone(String phone);

    // Ini semestinya void, karena tidak mengirim/ tdk mereturn value apapun.
    Person create(Person person);
    Person update(Person person);
    void deleteById(int id);
}
