package id.solvrtech.kontakjava.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetAction {
    void perform(ResultSet resultSet) throws SQLException;
}