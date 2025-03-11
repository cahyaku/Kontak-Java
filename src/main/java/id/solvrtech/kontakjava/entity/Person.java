package id.solvrtech.kontakjava.entity;

public class Person {
    // set private
    private int id;
    private String name;
    private String phone;

    public Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    // Method getter dan setter, ini fungsinya untuk dapat mengubah dan mengakses nilai variabel dari si objek.
    // Karena instance variable kali ini di-set menjadi private untuk menjaga keamanan (dikenal dgn encapsulation).
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
