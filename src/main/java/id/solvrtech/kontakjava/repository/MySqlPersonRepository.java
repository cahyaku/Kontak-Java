package id.solvrtech.kontakjava.repository;

import id.solvrtech.kontakjava.entity.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Saat extends dari BaseRepository bagian<T> wajib di isi dengan entity atau model class repository, sehingga modelnya adalah person.
 */
public class MySqlPersonRepository extends BaseRepository<Person> implements PersonRepository {
    // Ini untuk koneksi ke databasenya.
//    DatabaseConnection databaseConnection = new DatabaseConnection();

    public MySqlPersonRepository(DatabaseConnection databaseConnection) {
        super(databaseConnection); // ini untuk mendapatkan databaseConnection dari super classnya yakni BasePersonRepository.
    }

    /**
     * Note:
     * 1. Semua hasil dari ResultSet tidak bisa langusng di return begitu saja, maka kita wajib mengubah bentuknya
     * terlebih dahulu. Baik itu ke dalam array list, atupun ke dalam bentuk objek (misal person).
     * ---------
     * 2. Semua wajib dalam dibungkus dalam blok try/catch, untuk menangani kesalahan tak terduga saat kode dijalankan.
     * ---------
     * 3. constants connection tidak cocok di letakkan di setiap file repository, karena umumnya setiap project akan
     * memiliki banyak file repository. Tujuannya adalah menghindari copas constants secara berulang dimana-mana.
     * ---------
     * 4. Fungsi dari resultSet.next() adalah adalah untuk memindahkan kursor ke baris berikutnya dalam set hasil.
     * rs.next() mengembalikan true jika ada hasil, contoh penggunaannya if(rs.next()) berarti:
     * jika baris berikutnya bukan null (ada), lanjutkan.
     * ---------
     * 5. method statement.executedQuery(); dugunakan untuk mengeksekusi sql yang mengembalikan reslutSet,
     * yaitu data yang diambil dari database.
     **/

    @Override
    public List<Person> getAll() {
        return this.executeQueryForMultipleData("Select * FROM persons", null);

//        List<Person> persons = new ArrayList<>();
//        try (Connection conn = databaseConnection.createDBConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT * FROM persons")) {
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                persons.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getString("phone")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            databaseConnection.closeConnection();
//            databaseConnection.closeStatement();
//        }
//        return persons;
    }

    @Override
    public Person getById(int id) {
        String query = "SELECT * FROM persons WHERE id = ?";
        try (Connection conn = databaseConnection.createDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Person(id, rs.getString("name"), rs.getString("phone"));
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
        return this.executeQueryForMultipleData("SELECT * FROM persons WHERE name LIKE ?",
                stmt -> stmt.setString(1, "%" + name + "%"));

//        List<Person> persons = new ArrayList<>();
//        String query = "SELECT * FROM persons WHERE name LIKE ?";
//        try (Connection conn = databaseConnection.createDBConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            // Perlu diingat tanda % merupakan wildcard (karakter khusus) dalam operator LIKE
//            // untuk mewakili nol, satu, atau beberapa karakter.
//            stmt.setString(1, "%" + name + "%");
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                persons.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getString("phone")));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            databaseConnection.closeConnection();
//            databaseConnection.closeStatement();
//        }
//        return persons;
    }

    @Override
    public List<Person> getByPhone(String phone) {
        return this.executeQueryForMultipleData("SELECT * FROM persons WHERE phone LIKE ?",
                stmt -> stmt.setString(1, "%" + phone + "%"));

//        List<Person> persons = new ArrayList<>();
//        String query = "SELECT * FROM persons WHERE phone LIKE ?";
//
//        try (Connection conn = databaseConnection.createDBConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, "%" + phone + "%");
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                persons.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getString("phone")));
//            }
//            return persons;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            databaseConnection.closeConnection();
//            databaseConnection.closeStatement();
//        }
//        return persons;
    }

    @Override
    public Person create(Person person) {
        int generatedKey = this.executeCreate(
                "INSERT INTO persons (name, phone) VALUES (?, ?)",
                stmt -> {
                    stmt.setString(1, person.getName());
                    stmt.setString(2, person.getPhone());
                });
        return new Person(generatedKey, person.getName(), person.getPhone());

//        String query = "INSERT INTO persons (name, phone) VALUES (?, ?)";
//        try (Connection conn = databaseConnection.createDBConnection();
//             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, person.getName());
//            stmt.setString(2, person.getPhone());
//            stmt.executeUpdate();
//
//            ResultSet generatedKeys = stmt.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                person = new Person(generatedKeys.getInt(1), person.getName(), person.getPhone());
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            databaseConnection.closeConnection();
//            databaseConnection.closeStatement();
//        }
//        return person;
    }

    @Override
    public Person update(int id, Person person) {
        // Bagaian ini tidak bisa di return langsung, ya karena return value dari method update adalah object person.
        this.executeUpdate("UPDATE persons SET name = ?, phone = ? WHERE id = ?",
                stmt -> {
                    stmt.setString(1, person.getName());
                    stmt.setString(2, person.getPhone());
                    stmt.setInt(3, id);
                });

        return person;// sepertinya ini tidak perlu, dengan ubah return valuenya jadi void.

//        String query = "UPDATE persons SET name = ?, phone = ? WHERE id = ?";
//        try (Connection conn = databaseConnection.createDBConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, person.getName());
//            stmt.setString(2, person.getPhone());
//            stmt.setInt(3, person.getId());
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            databaseConnection.closeConnection();
//            databaseConnection.closeStatement();
//        }
//        return person;
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM persons WHERE id = ?";
        try (Connection conn = databaseConnection.createDBConnection();
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
    public boolean doesPhoneNumberExists(Integer id, String phoneNumber) {
        String query;
        if (id != null) {
            query = "SELECT COUNT(*) AS count FROM persons WHERE phone = ? AND id != ?";
        } else {
            query = "SELECT COUNT(*) AS count FROM persons WHERE phone = ?";
        }

        try (Connection conn = databaseConnection.createDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phoneNumber);

            if (id != null) {
                stmt.setInt(2, id); // Jika ada idnya, baru set statement id-nya.
            }

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
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

    @Override
    protected Person mapToEntity(ResultSet resultSet) throws SQLException {
        return new Person(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("phone"));
    }
}