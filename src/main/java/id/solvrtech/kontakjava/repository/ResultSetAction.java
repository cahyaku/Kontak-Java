package id.solvrtech.kontakjava.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ini juga mirip dengan prepared stament, ya agar urusan mengisi result action menjadi parameter
 * yang dapat diiis di MySqlPersonRepository.
 */
public interface ResultSetAction {
    void perform(ResultSet resultSet) throws SQLException;
}