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

    //    public MySqlPersonRepository() {
//        try {
//            Class.forName("com.mysql.cjbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private <T> T executeQuery(String query, QueryExecutor<T> executor) {
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)
//        ) {
//            return executor.execute(stament);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    // Helper method to run a query and process results
//    private <T> T executeQuery(String query, QueryProcessor<T> processor) {
//        try (Connection connection = DriverManager.getConnection(url, user, password);
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            return processor.process(statement);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

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
        String query = "SELECT * FROM persons WHERE id = " + id;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                return new Person(name, phone);
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
                persons.add(new Person(name, phone));
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
        return List.of();
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
        String query = "DELETE FROM persons WHERE id = " + id;
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
        if (id == null) {
            query = "SELECT * FROM persons WHERE phone ==" + phoneNumber;
        } else {
            query = "SELECT * FROM persons WHERE id != " + id + " AND phone = " + phoneNumber;
        }
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);){\
//            .....
//            }catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return false;
    }
}
