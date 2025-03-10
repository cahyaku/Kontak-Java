package id.solvrtech.kontakjava.repository;

import id.solvrtech.kontakjava.entity.Person;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class MySqlPersonRepository implements PersonRepository {
    // Menyiapakan parameter JDBC untuk koneksi ke database
    String DB_URL = "jdbc:mysql://localhost:33061/kontakjavadb";
    String USER = "root";
    String PASSWORD = "password";

    // Menyiapkan onjek yang diperlukan untuk mengelola database
    Connection conn;
    Statement stmt;
    ResultSet rs;

    // Beberapa hal yang belum:
    // 1. Ubah Connection agar tidak dilakukan secara berulang kali.
    // 2. semua wajib menggunakan close statement, sebaiknya menngunakan try catch yang diakhiri dengan finally.
    //    Kemudian letakan close stamentnya disana, untuk sekarng masih error, karena perlu throw Exception.
    // 3. update is phone exists dengan menggunakan query.
    // 4. tinggal deletenya saja.
    // 5. Jadi semua hasil dari ResultSet tidak bisa langsung di return begitu saja, maka kita wajib mengubah bentuknya
    //    terlebih dahulu baik itu ke dalam array list, ataupun ke dalam bentuk objek, misalnya person.
    
    @Override
    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM persons")) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Person person = new Person(rs.getNString("name"), rs.getString("phone"));
                person.setId(id);
                persons.add(person);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public Person getById(int id) {
        String query = "SELECT * FROM persons WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                return new Person(id, name, phone);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> getByName(String name) {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons WHERE name LIKE ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, name);

            // Perlu diingat tanda % merupakan wildcard (karakter khusus) dalam  operator LIKE
            // untuk mewakili nol, satu, atau beberapa karakter.
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String personName = rs.getString("name");
                String phone = rs.getString("phone");
                persons.add(new Person(id, personName, phone));
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public List<Person> getByPhone(String phone) {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons WHERE phone LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, phone);
            stmt.setString(1, "%" + phone + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone");
                persons.add(new Person(id, name, phoneNumber));
            }
            stmt.close();
            conn.close();
            return persons;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public Person create(Person person) {
        String query = "INSERT INTO persons (name, phone) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getPhone());
            stmt.executeUpdate();
            // Kita bisa langsung create seperti ini saja, ini tanpa perlu mengakses array Listnya dulu,
            // kemudian menambahkan dengan menggunakan method add.
            person = new Person(person.getName(), person.getPhone());
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public Person update(int id, Person person) {
        String query = "UPDATE persons SET name = ?, phone = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getPhone());
            stmt.setInt(3, person.getId());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM persons WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPhoneNumberExists(Integer id, String phoneNumber) {
        String query;
        if (id != null) {
            query = "SELECT * FROM persons WHERE phone = ? AND id != ?";
        } else {
            query = "SELECT * FROM persons WHERE phone = ?";
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phoneNumber);

            if (id != null) {
                stmt.setInt(2, id);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Mengembalikan nilai true jika ada satu nomor tlpn yang benar.
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
