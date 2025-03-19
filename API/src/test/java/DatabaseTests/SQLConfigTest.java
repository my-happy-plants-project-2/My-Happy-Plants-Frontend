package DatabaseTests;

import org.example.config.SQLConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SQLConfigTest {

    private SQLConfig sqlConfig;

    @BeforeEach
    void setUp() {
        sqlConfig = new SQLConfig();
    }

    /**
     * <h1>testGetConnection</h1>
     * <p>Test that the getConnection method works with the actual environmental variables for the database connection.</p>
     * @throws SQLException
     * @author Kasper Schröder
     */
    @Test
    void testGetConnection() throws SQLException {
        Connection conn = sqlConfig.getConnection();
        assertNotNull(conn);
        conn.close();
    }
}
