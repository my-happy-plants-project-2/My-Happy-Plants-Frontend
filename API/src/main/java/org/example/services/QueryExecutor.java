package org.example.services;

import org.example.config.ISQLConfig;
import org.example.config.SQLConfig;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for executing queries on the database
 * Contains methods for executing queries and transactions
 * on the database
 *
 * @author Kasper Schröder
 */
public class QueryExecutor implements IQueryExecutor {

    private static final Logger LOGGER = Logger.getLogger(SQLConfig.class.getName());
    private ISQLConfig sqlConfig;
    private Connection connection;

    /**
     * Constructor for QueryExecutor
     * @param sqlConfig the SQL configuration to be used for database operations
     *
     * @author Kasper Schröder
     */
    public QueryExecutor(ISQLConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
        this.connection = sqlConfig.getConnection();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * Executes an update query on the database
     * @param query the query to be executed
     * @param parameters the parameters to be used in the query
     *
     * @author Kasper Schröder
     */
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

    /**
     * Executes a query on the database
     * @param query the query to be executed
     * @param parameters the parameters to be used in the query
     * @return the result set of the query
     *
     * @author Kasper Schröder
     */
    @Override
    public ResultSet executeQuery(String query, Object... parameters) {
        try (Connection connection = sqlConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing query: " + query, e);
            return null;
        }
    }

    /**
     * Begins a transaction on the database
     *
     * @author Kasper Schröder
     */
    @Override
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error beginning transaction", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Ends a transaction on the database
     *
     * @author Kasper Schröder
     */
    @Override
    public void endTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error ending transaction", e);
        }
    }

    /**
     * Rolls back a transaction on the database
     *
     * @author Kasper Schröder
     */
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
