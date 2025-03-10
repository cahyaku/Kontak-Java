package id.solvrtech.kontakjava.repository;

import id.solvrtech.kontakjava.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlPersonRepository implements PersonRepository {
    // Ini untuk koneksi ke databasenya.
    DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * Note:
     * 1. Semua hasil dari ResultSet tidak bisa langusng di return begitu saja, maka kita wajib mengubah bentuknya
     * terlebih dahulu. Baik itu ke dalam array list, atupun ke dalam bentk objek (misal person).
     * ---------
     * 2. Semua wajib dalam dibungkus dalam blok try/catch, untuk menangani kesalahan tak terduga saat kode dijalankan.
     * ---------
     * 3. constants connection tidak cocok di letakkan di setiap file repository, karena umumnya setiap project akan
     * memiliki banyak file repository. Tujuannya adalah menghindari copas constants secara berulang dimana-mana.
     * ---------
     * 4. Fungsi dari resultSet.next() adalah adalah untuk memindahka kursor ke baris berikutnya dalam set hasil.
     * rs.next() mengembalikan true jika ada hasil, contoh penggunaannya if(rs.next()) berarti:
     * jika baris berikutnya bukan null (ada), lanjutkan.
     **/

    
    @Override
    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        try (
                Connection conn = databaseConnection.createDBConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM persons")) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Person person = new Person(rs.getNString("name"), rs.getString("phone"));
                person.setId(id);
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
        }
        return persons;
    }

    @Override
    public Person getById(int id) {
        String query = "SELECT * FROM persons WHERE id = ?";
        try (
                Connection conn = databaseConnection.createDBConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                return new Person(id, name, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
        }
        return null;
    }

    @Override
    public List<Person> getByName(String name) {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons WHERE name LIKE ?";
        try (
                Connection conn = databaseConnection.createDBConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            // stmt.setString(1, name);
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

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
        }
        return persons;
    }

    @Override
    public List<Person> getByPhone(String phone) {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons WHERE phone LIKE ?";

        try (
                Connection conn = databaseConnection.createDBConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            // stmt.setString(1, phone);
            stmt.setString(1, "%" + phone + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone");
                persons.add(new Person(id, name, phoneNumber));
            }
            return persons;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
        }
        return persons;
    }

    @Override
    public Person create(Person person) {
        String query = "INSERT INTO persons (name, phone) VALUES (?, ?)";
        try (
                Connection conn = databaseConnection.createDBConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getPhone());
            stmt.executeUpdate();
            // Kita bisa langsung create seperti ini saja, ini tanpa perlu mengakses array Listnya dulu,
            // kemudian menambahkan dengan menggunakan method add.
            person = new Person(person.getName(), person.getPhone());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
        }
        return person;
    }

    @Override
    public Person update(int id, Person person) {
        String query = "UPDATE persons SET name = ?, phone = ? WHERE id = ?";
        try (
                Connection conn = databaseConnection.createDBConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getPhone());
            stmt.setInt(3, person.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
        }
        return person;
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM persons WHERE id = ?";
        try (
                Connection conn = databaseConnection.createDBConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
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

        try (
                Connection conn = databaseConnection.createDBConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phoneNumber);

            if (id != null) {
                stmt.setInt(2, id); // Jika ada idnya, baru set statement id-nya.
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
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
        }
        return false;
    }
}
