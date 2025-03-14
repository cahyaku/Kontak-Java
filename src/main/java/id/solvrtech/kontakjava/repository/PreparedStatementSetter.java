package id.solvrtech.kontakjava.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Tujuan interface ini adalah untuk dapat mengirimkan preparedStatement ke masing-masing class yang menggilnya
 * yaitu di Baserepository, sehingga kelas yang implement dari BaseRepository dapat mengisi value dari preparedStament
 * di masing-masing methodnya yakni pada -> (MySqlPersonRepository).
 */
public interface PreparedStatementSetter {
    void setValues(PreparedStatement stmt) throws SQLException;
}