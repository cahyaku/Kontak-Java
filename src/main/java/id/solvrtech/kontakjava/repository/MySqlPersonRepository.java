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

//    // Harus dibungkus dalam block try/catch
//     try {
//        // buat koneksi ke database
//        conn = DriverManager.getConnection(DB_URL, USER, PASS);
//        // buat objek statement untuk melakukan query
//        stmt = conn.createStatement();
//        // query ke database dan tampilkan
//        rs = stmt.executeQuery("SELECT * FROM persons");
//        while (rs.next()) {
//            System.out.println("ID: " + rs.getInt("id"));
//            System.out.println("Name: " + rs.getString("name"));
//            System.out.println("Phone: " + rs.getString("phone"));
//        }
//        // jangan lupa untuk menutup statement dan koneksi spy tidak ada kebocoran memory
//        stmt.close();
//        conn.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }

    @Override
    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM persons")) {
            while (rs.next()) {
                // Dengan ini bisa saja langsung menampilkan datanya, jadi langsung panggila aja di app
//                System.out.println("ID: " + rs.getInt("id"));
//                System.out.println("Name: " + rs.getString("name"));
//                System.out.println("Phone: " + rs.getString("phone"));

                // Kalau ini ditampung didalam List dulu, agar bisa masuk ke method shoqw yang parameternya adalah List.
                persons.add(new Person(rs.getInt("id"), rs.getNString("name"), rs.getString("phone")));
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
//        } finally {
//
        }
        return persons;
    }

    @Override
    public Person getById(int id) {
        String query = "SELECT * FROM persons WHERE id = " + id;
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
        String query = "SELECT * FROM persons WHERE name LIKE ? + name";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String phone = rs.getString("phone");
                persons.add(new Person(id, name, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
        return persons;
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
