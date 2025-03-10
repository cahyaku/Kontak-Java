package id.solvrtech.kontakjava.repository;

import java.sql.*;

public class DatabaseConnection {
    // Menyiapakan parameter JDBC untuk koneksi ke database
    private String DB_URL = "jdbc:mysql://localhost:33061/kontakjavadb";
    private String USER = "root";
    private String PASSWORD = "password";

    // Menyiapkan objek yang diperlukan untuk mengelola database
    Connection conn;
    Statement stmt;
    ResultSet rs;

    /**
     * Method untuk membuat koneksi ke database
     * DriverManager,getConnection(), membuat koneksi ke DB dengan parameter DB_URL, USER, dan PASSWORD
     * Jika berhasil koneksinya akan disipan dalam objek conn, jika tidak maka akan throws SQLException.
     *
     * @return
     * @throws SQLException
     */
    public Connection createDBConnection() throws SQLException {
        return conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    /**
     * Method untuk menutup koneksi ke database.
     */
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close(); // Ini yg menutup koneksi ke database
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeStatement() {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
