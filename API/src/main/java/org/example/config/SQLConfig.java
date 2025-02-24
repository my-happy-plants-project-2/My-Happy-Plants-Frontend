package org.example.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLConfig implements ISQLConfig {
    //
    // TODO: Possibly implement HikariCP for connection pooling
    //

    // TODO: Implement SQLConfig and dont forget to use .env variables. NO username and password on git please.

    private static final Logger LOGGER = Logger.getLogger(SQLConfig.class.getName());
    private static Connection conn;

    private Connection createConnection() throws SQLException, UnknownHostException {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        if (url == null || user == null || password == null) {
            LOGGER.log(Level.SEVERE, "Database connection information is missing in the .env file");
        }

        SQLConfig.conn = DriverManager.getConnection(url, user, password);
        return conn;
    }


    @Override
    public Connection getConnection() {
        if (conn == null) {
            try {
                conn = createConnection();
            } catch (UnknownHostException | SQLException e) {
                LOGGER.log(Level.SEVERE, "Error creating database connection", e);
            }
        }
        return conn;
    }

    @Override
    public void closeConnection() {
        try {
            conn.close();
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error closing database connection", e);
        }
        conn = null;
    }
}
