package org.example.services;

import org.example.config.ISQLConfig;
import org.example.config.SQLConfig;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryExecutor implements IQueryExecutor {

    private static final Logger LOGGER = Logger.getLogger(SQLConfig.class.getName());
    private ISQLConfig sqlConfig;
    private Connection connection;

    public QueryExecutor(ISQLConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
        this.connection = sqlConfig.getConnection();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void executeUpdate(String query, Object... parameters) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing update", e);
        }

    }

    @Override
    public ResultSet executeQuery(String query, Object... parameters) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing query", e);
        }
        return null;
    }

    @Override
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error beginning transaction", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error ending transaction", e);
        }
    }

    @Override
    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error rolling back transaction", e);
        }
    }
}
