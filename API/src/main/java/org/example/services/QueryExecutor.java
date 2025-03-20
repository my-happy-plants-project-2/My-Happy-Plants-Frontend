package org.example.services;

import org.example.config.ISQLConfig;
import org.example.config.SQLConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A service class responsible for executing SQL queries and managing transactions.
 * This class provides methods to execute update queries, fetch query results,
 * and handle transactions within a database.
 *
 * <p>Implements the {@link IQueryExecutor} interface.</p>
 *
 * @author Kasper Schröder
 */
public class QueryExecutor implements IQueryExecutor {

    private static final Logger LOGGER = Logger.getLogger(SQLConfig.class.getName());
    private ISQLConfig sqlConfig;
    private Connection connection;

    /**
     * Initializes a new instance of QueryExecutor using the provided SQL configuration.
     *
     * @param sqlConfig the SQL configuration used for database operations, including the connection details.
     */
    public QueryExecutor(ISQLConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
        this.connection = sqlConfig.getConnection();
    }

    /**
     * Retrieves the current database connection associated with this executor.
     *
     * @return the active {@link Connection} instance.
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * Executes an update query such as INSERT, UPDATE, or DELETE.
     *
     * @param query the SQL query to execute.
     * @param parameters the parameters to be substituted into the query.
     * @return {@code true} if the update was successful, {@code false} otherwise.
     */
    @Override
    public boolean executeUpdate(String query, Object... parameters) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing update", e);
            return false;
        }
        return true;
    }

    /**
     * Executes a SELECT query and retrieves the result set as a list of maps.
     * Each row in the result set is represented as a {@link Map} where the keys are column names,
     * and the values are the corresponding data entries.
     *
     * @param query      the SQL SELECT query to execute.
     * @param parameters the parameters to be substituted into the query.
     * @return a list of maps representing the query results, or {@code null} if an error occurs.
     */
    @Override
    public List<Map<String, Object>> executeQuery(String query, Object... parameters) {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection connection = sqlConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Map<String, Object> row = new TreeMap<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                results.add(row);
            }
            return results;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing query: " + query, e);
            return null;
        }
    }

    /**
     * Begins a new database transaction by disabling auto-commit mode.
     * This allows multiple operations to be executed as a single unit of work.
     *
     * @throws RuntimeException if an error occurs while starting the transaction.
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
     * Commits the current transaction, making all changes permanent.
     * Auto-commit mode is then re-enabled.
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
     * Rolls back the current transaction, undoing any changes made since the transaction began.
     * Auto-commit mode is then re-enabled.
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
