package id.solvrtech.kontakjava.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ini adalah class yang memiliki parameter, class ini dapat menerima tipe objek yang tidak ditentukan.
 *
 * @param <T>
 */
public abstract class BaseRepository<T> {
    // or any database connection wrapper class that you use.
    protected DatabaseConnection databaseConnection;

    public BaseRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Some Notes:
     * 1). prepareStatement(): adalah perintah untuk mengeksekusi SQL.
     * Jika prepareStatement(query, stmt.RETURN_GENERATE_KEYS), parameter ini -> stmt.RETURN_GENERATE_KEYS
     * yang akan mengabil key  yang dibuat secara otomatis, ini digunakan  untuk pernyataan INSERT INTO (untuk menambahkan data baru ke dalam tabel yang sudah ada).
     * ---------
     * 2). Abstract class dapat di perluas oleh subkelas, sehingga kelas abstrak akan memiliki perilaku umum yang dapat diwariskan
     * 3). Kelas abstract hanya memiliki kerangka tanpa detail, jadi detailnya akan diisi oleh turunannya.
     */

    /**
     * Maps the given ResultSet into T
     *
     * @param resultSet {@link ResultSet}
     * @return T
     */
    protected abstract T mapToEntity(ResultSet resultSet) throws SQLException;

    /**
     * Executes a general SQL query, with optional PreparedStatement setter for binding any query value, and optional
     * ResultSetAction for performing any action / codes which will be taken once the query has been done.
     *
     * @param query  String
     * @param setter {@link PreparedStatementSetter}
     */
    protected void executeQuery(String query, PreparedStatementSetter setter, ResultSetAction action) {
        try (Connection conn = databaseConnection.createDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (setter != null) {
                setter.setValues(stmt);
            }

            ResultSet rs = stmt.executeQuery(); // dan posisi ini juga mempengaruhi.
            // Karena jika dilakukan sebelum setter : No value specified for parameter 1 :)

            if (action != null) {
                action.perform(rs); // ini belum tau apa fungsi dan kegunaanya (mungkin menamoung rs dari intercaface ResultSetAction).
            }
        } catch (SQLException e) {
            e.printStackTrace(); // ini untuk menangkan exception ketika terjadi error saat createDBConnetion.
        }
    }

    /**
     * Executes an SQL query for getting a single data (converted into T), with optional PreparedStatement setter for
     * binding any query value.
     *
     * @param query  String
     * @param setter {@link PreparedStatementSetter}
     * @return T
     */
    protected T executeQueryForSingleData(String query, PreparedStatementSetter setter) {
        List<T> temp = new ArrayList<>(); // ini untuk menampung hasil resultnya.

        executeQuery(query, setter, result -> { // tahap ini belum paham, baru pakai auto complete saja.
            temp.add(mapToEntity(result));
        });

        return null;
    }

    /**
     * Executes a SQL query for getting list of data (converted into List of T), with optional PreparedStatement setter
     * for binding any query value.
     *
     * @param query  String
     * @param setter {@link PreparedStatementSetter}
     * @return list of T
     */
    protected List<T> executeQueryForMultipleData(String query, PreparedStatementSetter setter) {
        List<T> temp = new ArrayList<>();
        try (
                Connection conn = databaseConnection.createDBConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            executeQuery(query, setter, result -> {
                /**
                 * error: java.sql.SQLException: Before start of result set, jika tidak menggunakan next.
                 * ResultSet menunjuk ke posisi di dalam hasil query saat pertama kali membuat ResultSet.
                 * Jadi pointer berada sebelum baris pertama hasil.
                 * Maka perlu memindahkan pointer tsb ke baris pertama dengan next() sebelum mencoba mengakses datanya.
                 */
                while (result.next()) { // Menindahkan kursor ke baris berikutnya.
                    temp.add(mapToEntity(result)); // Mengakses data setelah next();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
        }
        return temp;

    }

    /**
     * Executes an SQL "insert" query with the given query and the optional PreparedStatement setter for binding any
     * query value.
     *
     * @param query  String
     * @param setter {@link PreparedStatementSetter}
     * @return last generated ID of the SQL insert query
     */
    protected int executeCreate(String query, PreparedStatementSetter setter) {
        try (Connection conn = databaseConnection.createDBConnection();
             // ini sekaligus untuk generated key nya.
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            if (setter != null) {
                setter.setValues(stmt);
            }

            stmt.executeUpdate(); // ingat ini dilakukan setelah

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
        }
        return 0;
    }

    /**
     * Executes an SQL "update" query with the given query and the optional PreparedStatement setter for binding any
     * query value.
     *
     * @param query  String
     * @param setter {@link PreparedStatementSetter}
     */
    protected void executeUpdate(String query, PreparedStatementSetter setter) {
        try (Connection conn = databaseConnection.createDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (setter != null) {
                setter.setValues(stmt);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
            databaseConnection.closeStatement();
        }
    }

    /**
     * Executes an SQL "delete" query with the given query and the optional PreparedStatement setter for binding any
     * query value.
     *
     * @param query  String
     * @param setter {@link PreparedStatementSetter}
     */
    protected void executeDelete(String query, PreparedStatementSetter setter) {
        this.executeUpdate(query, setter);
    }
}