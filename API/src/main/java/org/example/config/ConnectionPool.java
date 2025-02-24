package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.config.ISQLConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static DataSource dataSource;

    private static DataSource createDataSource() {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");


        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(3);

        return new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            dataSource = createDataSource();
        }
        return dataSource.getConnection();
    }


    public void closeConnection() throws Exception {
        if (dataSource instanceof HikariDataSource)
        {
            ((HikariDataSource) dataSource).close();
        }
        dataSource = null;

    }
}
