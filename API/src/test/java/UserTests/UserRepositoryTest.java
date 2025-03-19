package UserTests;

import org.example.repositories.UserRepository;
import org.example.services.IQueryExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
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

    /**
     * <h1>testAddUser_Success</h1>
     * <p>Test a successful mock of the addUser method in UserRepository.</p>
     * <p>Creates variables for email, username, password, and colorTheme.</p>
     * <ul>
     *  <li>Mocks the beginTransaction method for queryExecutor to do nothing when called.</li>
     *  <li>Mocks the executeUpdate method for queryExecutor to return true when called.</li>
     *  <li>Mocks the endTransaction method for queryExecutor to do nothing when called.</li>
     * </ul>
     * <p>
     * Calls the addUser method in userRepository with the email, username, password, and colorTheme variables, and
     * stores the result in a boolean.
     * <ul>
     *  <li>Asserts that the result is true.</li>
     *  <li>Verifies that the beginTransaction method was called.</li>
     *  <li>Verifies that the executeUpdate method was called with the email, username, password, and colorTheme variables.</li>
     *  <li>Verifies that the endTransaction method was called.</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testAddUser_Success() {
        String email = "test@example.com";
        String username = "testuser";
        String password = "password123";
        String colorTheme = "1";

        doNothing().when(queryExecutor).beginTransaction();
        doReturn(true).when(queryExecutor).executeUpdate(anyString(), anyString(), anyString(), anyString(), anyInt());
        doNothing().when(queryExecutor).endTransaction();

        boolean result = userRepository.addUser(email, username, password, colorTheme);

        assertTrue(result);
        verify(queryExecutor).beginTransaction();
        verify(queryExecutor).executeUpdate(anyString(), eq(email), eq(username), anyString(), eq(Integer.parseInt(colorTheme)));
        verify(queryExecutor).endTransaction();
    }

    /**
     * <h1>testAddUser_Failure</h1>
     * <p>Test a failed mock of the addUser method in UserRepository.</p>
     * <p>Creates variables for email, username, password, and colorTheme.</p>
     * <ul>
     *  <li>Mocks the beginTransaction method for queryExecutor to do nothing when called.</li>
     *  <li>Mocks an error being thrown when the executeUpdate method for queryExecutor is called.</li>
     *  <li>Mocks the rollbackTransaction method for queryExecutor to do nothing when called.</li>
     * </li>
     * <p>
     * Calls the addUser method in userRepository with the email, username, password, and colorTheme variables, and
     * stores the result in a boolean.
     * <ul>
     *  <li>Asserts that the result is false.</li>
     *  <li>Verifies that the beginTransaction method was called.</li>
     *  <li>Verifies that the executeUpdate method was called with the email, username, password, and colorTheme variables.</li>
     *  <li>Verifies that the rollbackTransaction method was called.</li>
     * <li>
     * @author Kasper Schröder
     */
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


    /**
     * <h1>testCheckLogin_Success</h1>
     * <p>Test a successful mock of the checkLogin method in UserRepository.</p>
     * <p>
     * Creates variables for email and password.
     * Hashes the password using BCrypt.
     * <p>
     * Creates a map of the hashed password.
     * Creates a list of the map.
     * <ul>
     *  <li>Mocks the executeQuery method for queryExecutor to return the list of maps when called.</li>
     * </ul>
     * <p>
     * Calls the checkLogin method in userRepository with the email and password variables, and stores the result
     * in a boolean.
     * <ul>
     *  <li>Asserts that the result is true.</li>
     *  <li>Verifies that the executeQuery method was called with the email variable.</li>
     * </ul>
     * @author Kasper Schröder
     */
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

    /**
     * <h1>testCheckLogin_Failure.</h1>
     * <p>Test a failed mock of the checkLogin method in UserRepository.</p>
     * <p>Creates variables for email and password.</p>
     * <p>
     * Creates an empty list of maps.
     * <ul>
     *  <li>Mocks the executeQuery method for queryExecutor to return the empty list of maps when called.</li>
     * </ul>
     * <p>
     * Calls the checkLogin method in userRepository with the email and password variables, and stores the result
     * in a boolean.
     * <ul>
     *  <li>Asserts that the result is false.</li>
     *  <li>Verifies that the executeQuery method was called with the email variable.</li>
     * <li>
     * @author Kasper Schröder
     */
    @Test
    void testCheckLogin_Failure() {
        String email = "test@example.com";
        String password = "password123";

        List<Map<String, Object>> results = List.of();

        when(queryExecutor.executeQuery(anyString(), eq(email))).thenReturn(results);

        boolean result = userRepository.checkLogin(email, password);

        assertFalse(result);
        verify(queryExecutor).executeQuery(anyString(), eq(email));
    }


    /**
     * <h1>testAddOwnerPlant_Success</h1>
     * <p>Test a successful mock of the addOwnerPlant method in UserRepository.</p>
     * <p>Creates variables for plantID, nickname, owner, note, species and description.</p>
     * <ul>
     * <li>Mocks the beginTransaction method for queryExecutor to do nothing when called.</li>
     *  <li>Mocks the executeUpdate method for queryExecutor to return true when called.</li>
     *  <li>Mocks the endTransaction method for queryExecutor to do nothing when called.</li>
     * </ul>
     * <p>
     * Calls the waterPlant method in userRepository with the plantID, nickname, owner, note, species, and description
     * variables, and stores the result in a boolean.
     * <ul>
     *  <li>Asserts that the result is true.</li>
     *  <li>Verifies that the beginTransaction method was called.</li>
     *  <li>Verifies that the executeUpdate method was called with the plantID, nickname, owner, note, species, and description.</li>
     *  <li>Verifies that the endTransaction method was called.</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testAddOwnerPlant_Success() {
        String plantID = "1";
        String nickname = "My Rose";
        String owner = "test@example.com";
        String note = "Needs water";
        String species = "Rose";
        String description = "A beautiful rose";

        doNothing().when(queryExecutor).beginTransaction();
        doReturn(true).when(queryExecutor).executeUpdate(anyString(), eq(plantID), eq(nickname), eq(owner), eq(note), eq(species), any(Date.class));
        doNothing().when(queryExecutor).endTransaction();

        boolean result = userRepository.addOwnerPlant(plantID, nickname, owner, note, species, description);

        assertTrue(result);
        verify(queryExecutor).beginTransaction();
        verify(queryExecutor).executeUpdate(anyString(), eq(plantID), eq(nickname), eq(owner), eq(note), eq(species), any(Date.class));
        verify(queryExecutor).endTransaction();
    }

    /**
     * <h1>testAddOwnerPlant_Failure</h1>
     * <p>Test a failed mock of the addOwnerPlant method in UserRepository.</p>
     * <p>Creates variables for plantID, nickname, owner, note, species and description.</p>
     * <ul>
     *  <li>Mocks the beginTransaction method for queryExecutor to do nothing when called.</li>
     *  <li>Mocks an error being thrown when the executeUpdate method for queryExecutor is called.</li>
     *  <li>Mocks the rollbackTransaction method for queryExecutor to do nothing when called.</li>
     * </ul>
     * <p>
     * Calls the waterPlant method in userRepository with the plantID, nickname, owner, note, species, and description
     * variables, and stores the result in a boolean.
     * <ul>
     *  <li>Asserts that the result is false.</li>
     *  <li>Verifies that the beginTransaction method was called.</li>
     *  <li>Verifies that the executeUpdate method was called with the plantID, nickname, owner, note, species,
     *  and description.</li>
     *  <li>Verifies that the rollbackTransaction method was called.</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testAddOwnerPlant_Failure() {
        String plantID = "1";
        String nickname = "My Rose";
        String owner = "test@example.com";
        String note = "Needs water";
        String species = "Rose";
        String description = "A beautiful rose";

        doNothing().when(queryExecutor).beginTransaction();
        doThrow(new RuntimeException("Error")).when(queryExecutor).executeUpdate(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(Date.class));
        doNothing().when(queryExecutor).rollbackTransaction();

        boolean result = userRepository.addOwnerPlant(plantID, nickname, owner, note, species, description);

        assertFalse(result);
        verify(queryExecutor).beginTransaction();
        verify(queryExecutor).executeUpdate(anyString(), eq(plantID), eq(nickname), eq(owner), eq(note), eq(species), any(Date.class));
        verify(queryExecutor).rollbackTransaction();
    }
}
