package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLConfig implements ISQLConfig {

    private static final Logger LOGGER = Logger.getLogger(SQLConfig.class.getName());
    private static DataSource dataSource;

    private DataSource createDataSource() {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        if (url == null || user == null || password == null) {
            LOGGER.log(Level.SEVERE, "Database connection information is missing in the .env file");
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(3);

        return new HikariDataSource(config);
    }

    @Override
    public Connection getConnection() {
        try {
            if (dataSource == null) {
                dataSource = createDataSource();
            }
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting database connection", e);
            return null;
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (dataSource instanceof HikariDataSource) {
                ((HikariDataSource) dataSource).close();
            }
            dataSource = null;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error closing data source", e);
        }

    }
}
