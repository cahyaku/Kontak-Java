package id.solvrtech.kontakjava.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Person {
    // set private
    private int id;
    private String name;
    private String phone;

    // Sama seperti saat membuat small bank bagian account.
//    public Person(String name, String phone) {
//        this.name = name;
//        this.phone = phone;
//    }

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    // Method getter dan setter, ini fungsinya untuk dapat mengubah dan mengakses nilai variabel dari si objek.
    // Karena instance variable kali ini di-set menjadi private untuk menjaga keamaanan (dikenal dgn encapsulation).
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
