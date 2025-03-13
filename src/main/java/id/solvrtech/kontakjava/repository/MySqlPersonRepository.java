package id.solvrtech.kontakjava.repository;

import id.solvrtech.kontakjava.entity.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Saat extends dari BaseRepository bagian<T> wajib di isi dengan entity atau model class repository,
 * sehingga modelnya adalah person.
 */
public class MySqlPersonRepository extends BaseRepository<Person> implements PersonRepository {

    public MySqlPersonRepository() {
        // Ini untuk mendapatkan databaseConnection dari super classnya yakni BasePersonRepository.
        super(new DatabaseConnection());
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
    }

    @Override
    public Person getById(int id) {
        return this.executeQueryForSingleData("SELECT * FROM persons WHERE id = ?",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement stmt) throws SQLException {
                        stmt.setInt(1, id);
                    }
                });
    }

    @Override
    public List<Person> getByName(String name) {
        return this.executeQueryForMultipleData("SELECT * FROM persons WHERE name LIKE ?",
                stmt -> stmt.setString(1, "%" + name + "%"));
    }

    @Override
    public List<Person> getByPhone(String phone) {
        return this.executeQueryForMultipleData("SELECT * FROM persons WHERE phone LIKE ?",
                stmt -> stmt.setString(1, "%" + phone + "%"));
    }

    @Override
    public Person create(Person person) {
        int generatedKey = this.executeCreate(
                "INSERT INTO persons (name, phone) VALUES (?, ?)",
                stmt -> {
                    stmt.setString(1, person.getName());
                    String phoneNumber = (person.getPhone().startsWith("+62")) ?
                            person.getPhone().replaceFirst("\\+62", "0") :
                            person.getPhone();
                    stmt.setString(2, phoneNumber);
                });
        return new Person(generatedKey, person.getName(), person.getPhone());
    }

    @Override
    public Person update(int id, Person person) {
        // Bagian ini tidak bisa di return langsung, ya karena return value dari method update adalah object person.
        this.executeUpdate("UPDATE persons SET name = ?, phone = ? WHERE id = ?",
                stmt -> {
                    stmt.setString(1, person.getName());
                    stmt.setString(2, person.getPhone());
                    stmt.setInt(3, id);
                });

        return person;// Sepertinya ini tidak perlu, dengan ubah return valuenya jadi void.
    }

    @Override
    public void deleteById(int id) {
        executeDelete("DELETE FROM persons WHERE id = ?",
                stmt -> stmt.setInt(1, id));
    }

    @Override
    public boolean doesPhoneNumberExists(Integer id, String phoneNumber) {
        String query;
        if (id != null) {
            query = "SELECT COUNT(*) AS count FROM persons WHERE phone = ? AND id != ?";
        } else {
            query = "SELECT COUNT(*) AS count FROM persons WHERE phone = ?";
        }

        List<Integer> temp = new ArrayList<Integer>(1);
        this.executeQuery(query, stmt -> {
            stmt.setString(1, (phoneNumber));
            if (id != null) {
                stmt.setInt(2, id);
            }
        }, resultSet -> {
            if (resultSet.next()) {
                temp.add(resultSet.getInt("count"));
            }
        });
        return temp.get(0) > 0;
    }

    @Override
    protected Person mapToEntity(ResultSet resultSet) throws SQLException {
        return new Person(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("phone"));
    }
}