package DatabaseTests;

import org.example.config.ISQLConfig;
import org.example.services.QueryExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class QueryExecutorTest {

    @Mock
    private ISQLConfig sqlConfig;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private QueryExecutor queryExecutor;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(sqlConfig.getConnection()).thenReturn(connection);
        queryExecutor = new QueryExecutor(sqlConfig);
    }

    @Test
    void testExecuteUpdate() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        queryExecutor.executeUpdate("UPDATE users SET username = ? WHERE email = ?", "newUsername", "test@example.com");

        verify(connection).prepareStatement("UPDATE users SET username = ? WHERE email = ?");
        verify(preparedStatement).setObject(1, "newUsername");
        verify(preparedStatement).setObject(2, "test@example.com");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testExecuteQuery() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Map<String, Object>> result = queryExecutor.executeQuery("SELECT * FROM users WHERE email = ?", 1);

        assertNotNull(result);
        verify(connection).prepareStatement("SELECT * FROM users WHERE email = ?");
        verify(preparedStatement).setObject(1, 1);
        verify(preparedStatement).executeQuery();
    }
}
