package id.solvrtech.kontakjava.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {
    void setValues(PreparedStatement stmt) throws SQLException;
}