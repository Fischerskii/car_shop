package ru.ylab.hw.repository;

import ru.ylab.hw.exception.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractRepository {

    protected void rollbackConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DataAccessException("Failed to rollback transaction", e);
            }
        }
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                throw new DataAccessException("Failed to close connection", e);
            }
        }
    }
}
