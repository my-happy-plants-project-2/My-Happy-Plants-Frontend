package UserTests;

import org.example.repositories.UserRepository;
import org.example.services.IQueryExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    @Mock
    private IQueryExecutor queryExecutor;

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository = new UserRepository(queryExecutor);
    }

    @Test
    void testAddUser_Success() {
        String email = "test@example.com";
        String username = "testuser";
        String password = "password123";
        String colorTheme = "1";

        doNothing().when(queryExecutor).beginTransaction();
        doNothing().when(queryExecutor).executeUpdate(anyString(), anyString(), anyString(), anyString(), anyInt());
        doNothing().when(queryExecutor).endTransaction();

        boolean result = userRepository.addUser(email, username, password, colorTheme);

        assertTrue(result);
        verify(queryExecutor).beginTransaction();
        verify(queryExecutor).executeUpdate(anyString(), eq(email), eq(username), anyString(), eq(Integer.parseInt(colorTheme)));
        verify(queryExecutor).endTransaction();
    }

    @Test
    void testAddUser_Failure() {
        String email = "test@example.com";
        String username = "testuser";
        String password = "password123";
        String colorTheme = "1";

        doNothing().when(queryExecutor).beginTransaction();
        doThrow(new RuntimeException("Error")).when(queryExecutor).executeUpdate(anyString(), anyString(), anyString(), anyString(), anyInt());
        doNothing().when(queryExecutor).rollbackTransaction();

        boolean result = userRepository.addUser(email, username, password, colorTheme);

        assertFalse(result);
        verify(queryExecutor).beginTransaction();
        verify(queryExecutor).executeUpdate(anyString(), eq(email), eq(username), anyString(), eq(Integer.parseInt(colorTheme)));
        verify(queryExecutor).rollbackTransaction();
    }

    @Test
    void testCheckLogin_Success() {
        String email = "test@example.com";
        String password = "password123";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Map<String, Object> resultMap = Map.of("password", hashedPassword);
        List<Map<String, Object>> results = List.of(resultMap);

        when(queryExecutor.executeQuery(anyString(), eq(email))).thenReturn(results);

        boolean result = userRepository.checkLogin(email, password);

        assertTrue(result);
        verify(queryExecutor).executeQuery(anyString(), eq(email));
    }
}
