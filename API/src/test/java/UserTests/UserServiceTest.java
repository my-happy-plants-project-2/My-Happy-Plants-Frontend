package UserTests;

import io.javalin.http.Context;
import org.example.model.User;
import org.example.repositories.UserRepository;
import org.example.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class UserServiceTest {
    @Mock
    private Context context;

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() throws Exception {
        try (AutoCloseable closeable = MockitoAnnotations.openMocks(this)) {
            userService = new UserService(userRepository);
        }
    }

    /**
     * <h1>testAddUser_Success</h1>
     * <p>Test a successful mock of the addUser method in UserService.</p>
     * <ul>
     *  <li>Creates a mock User object.</li>
     *  <li>Mocks the bodyAsClass method for context to return the mock User object when called.</li>
     *  <li>Mocks the addUser method for userRepository to return true when called.</li>
     *  <li>Mocks the status method for context to return context when called.</li>
     * </ul>
     * <p>
     * Calls the addUser method in userService with the context variable.
     * <ul>
     *  <li>Verifies that the context status method was called with 201.</li>
     *  <li>Verifies that the context result method was called with "User created successfully".</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testAddUser_Success() {
        User mockUser = new User("testuser", "test@example.com", "password123", 1);
        when(context.bodyAsClass(User.class)).thenReturn(mockUser);
        when(userRepository.addUser(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        when(context.status(anyInt())).thenReturn(context);

        userService.addUser(context);

        verify(context).status(201);
        verify(context).result("User created successfully");
    }

    /**
     * <h1>testAddUser_Failure</h1>
     * <p>Test a failed mock of the addUser method in UserService.</p>
     * <ul>
     *  <li>Creates a mock User object.</li>
     *  <li>Mocks the bodyAsClass method for context to return the mock User object when called.</li>
     *  <li>Mocks the addUser method for userRepository to return false when called.</li>
     *  <li>Mocks the status method for context to return context when called.</li>
     * </ul>
     * <p>Calls the addUser method in userService with the context variable.</p>
     * <ul>
     *  <li>Verifies that the context status method was called with 500.</li>
     *  <li>Verifies that the context result method was called with "Error creating user".</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testAddUser_Failure() {
        User mockUser = new User("testuser", "test@example.com", "password123", 1);
        when(context.bodyAsClass(User.class)).thenReturn(mockUser);
        when(userRepository.addUser(anyString(), anyString(), anyString(), anyString())).thenReturn(false);
        when(context.status(anyInt())).thenReturn(context);

        userService.addUser(context);

        verify(context).status(500);
        verify(context).result("Error creating user");
    }

    /**
     * <h1>testLogin_Success</h1>
     * <p>Test a successful mock of the login method in UserService.</p>
     * <ul>
     *  <li>Creates a mock User object.</li>
     *  <li>Mocks the bodyAsClass method for context to return the mock User object when called.</li>
     *  <li>Mocks the checkLogin method for userRepository to return true when called.</li>
     *  <li>Mocks the status method for context to return context when called.</li>
     * </ul>
     * <p>Calls the login method in userService with the context variable.</p>
     * <ul>
     *  <li>Verifies that the context json method was called with anyMap.</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testLogin_Success() {
        User mockUser = new User("testuser", "test@example.com", "password123", 1);
        when(context.bodyAsClass(User.class)).thenReturn(mockUser);
        when(userRepository.checkLogin(anyString(), anyString())).thenReturn(true);
        when(context.status(anyInt())).thenReturn(context);

        userService.login(context);

        verify(context).json(anyMap());
    }

    /**
     * <h1>testLogin_Failure_InvalidCredentials</h1>
     * <p>Test a failed mock of the login method in UserService with invalid credentials.</p>
     * <ul>
     *  <li>Creates a mock User object.</li>
     *  <li>Mocks the bodyAsClass method for context to return the mock User object when called.</li>
     *  <li>Mocks the checkLogin method for userRepository to return false when called.</li>
     *  <li>Mocks the status method for context to return context when called.</li>
     * </ul>
     * <p>Calls the login method in userService with the context variable.</p>
     * <ul>
     *  <li>Verifies that the context status method was called with 401.</li>
     *  <li>Verifies that the context result method was called with "Invalid email or password".</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testLogin_Failure_InvalidCredentials() {
        User mockUser = new User("testuser", "test@example.com", "password123", 1);
        when(context.bodyAsClass(User.class)).thenReturn(mockUser);
        when(userRepository.checkLogin(anyString(), anyString())).thenReturn(false);
        when(context.status(anyInt())).thenReturn(context);

        userService.login(context);

        verify(context).status(401);
        verify(context).result("Invalid email or password");
    }

    /**
     * <h1>testLogin_Failure_MissingEmail</h1>
     * <p>Test a failed mock of the login method in UserService with missing email.</p>
     * <ul>
     *  <li>Creates a mock User object with a null email.</li>
     *  <li>Mocks the bodyAsClass method for context to return the mock User object when called.</li>
     *  <li>Mocks the status method for context to return context when called.</li>
     * </ul>
     * <p>Calls the login method in userService with the context variable.</p>
     * <ul>
     *  <li>Verifies that the context status method was called with 400.</li>
     *  <li>Verifies that the context result method was called with "Email and password must be provided".</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testLogin_Failure_MissingEmail() {
        User mockUser = new User("testuser", null, "password123", 1); // Email is null
        when(context.bodyAsClass(User.class)).thenReturn(mockUser);
        when(context.status(anyInt())).thenReturn(context);

        userService.login(context);

        verify(context).status(400);
        verify(context).result("Email and password must be provided");
    }

    /**
     * <h1>testLogin_Failure_MissingPassword</h1>
     * <p>Test a failed mock of the login method in UserService with missing password.</p>
     * <ul>
     *  <li>Creates a mock User object with a null password.</li>
     *  <li>Mocks the bodyAsClass method for context to return the mock User object when called.</li>
     *  <li>Mocks the status method for context to return context when called.</li>
     * </ul>
     * <p>Calls the login method in userService with the context variable.</p>
     * <ul>
     *  <li>Verifies that the context status method was called with 400.</li>
     *  <li>Verifies that the context result method was called with "Email and password must be provided".</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testLogin_Failure_MissingPassword() {
        User mockUser = new User("testuser", "test@example.com", null, 1); // Password is null
        when(context.bodyAsClass(User.class)).thenReturn(mockUser);
        when(context.status(anyInt())).thenReturn(context);

        userService.login(context);

        verify(context).status(400);
        verify(context).result("Email and password must be provided");
    }


    /**
     * <h1>testAddPlantToUserLibrary_Success</h1>
     * <p>Test a successful mock of the addPlantToUserLibrary method in UserService.</p>
     * <ul>
     *  <li>Mocks the body method for context to return a JSON string with plant_id, species, and nickname when called.</li>
     *  <li>Mocks the header method for context to return a valid token when called.</li>
     *  <li>Mocks the addOwnerPlant method for userRepository to return true when called.</li>
     *  <li>Mocks the status method for context to return context when called.</li>
     * </ul>
     * <p>Calls the addPlantToUserLibrary method in userService with the context variable.</p>
     * <ul>
     *  <li>Verifies that the context status method was called with 201.</li>
     *  <li>Verifies that the context result method was called with "Plant added to user library".</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testAddPlantToUserLibrary_Success() {
        when(context.body()).thenReturn("{\"plant_id\":\"1\",\"species\":\"Rose\",\"nickname\":\"My Rose\"}");
        when(context.header("Authorization")).thenReturn("Bearer someValidToken");
        when(userRepository.addOwnerPlant(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        when(context.status(anyInt())).thenReturn(context);

        userService.addPlantToUserLibrary(context);

        verify(context).status(201);
        verify(context).result("Plant added to user library");
    }

    /**
     * <h1>testAddPlantToUserLibrary_Failure</h1>
     * <p>Test a failed mock of the addPlantToUserLibrary method in UserService.</p>
     * <ul>
     *  <li>Mocks the body method for context to return a JSON string with plant_id, species, and nickname when called.</li>
     *  <li>Mocks the header method for context to return a valid token when called.</li>
     *  <li>Mocks the addOwnerPlant method for userRepository to return false when called.</li>
     *  <li>Mocks the status method for context to return context when called.</li>
     * </ul>
     * <p>Calls the addPlantToUserLibrary method in userService with the context variable.</p>
     * <ul>
     *  <li>Verifies that the context status method was called with 500.</li>
     *  <li>Verifies that the context result method was called with "Error adding plant to user library".</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testAddPlantToUserLibrary_Failure() {
        when(context.body()).thenReturn("{\"plant_id\":\"1\",\"species\":\"Rose\",\"nickname\":\"My Rose\"}");
        when(context.header("Authorization")).thenReturn("Bearer someValidToken");
        when(userRepository.addOwnerPlant(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(false);
        when(context.status(anyInt())).thenReturn(context);

        userService.addPlantToUserLibrary(context);

        verify(context, times(2)).status(500);
        verify(context).result("Error adding plant to user library");
    }

    /**
     * <h1>testAddPlantToUserLibrary_InvalidRequestBody</h1>
     * <p>Test a failed mock of the addPlantToUserLibrary method in UserService with an invalid request body.</p>
     * <ul>
     *  <li>Mocks the body method for context to return an invalid JSON string when called.</li>
     *  <li>Mocks the status method for context to return context when called.</li>
     * </ul>
     * <p>Calls the addPlantToUserLibrary method in userService with the context variable.</p>
     * <ul>
     *  <li>Verifies that the context status method was called with 400.</li>
     *  <li>Verifies that the context result method was called with "Invalid request body".</li>
     * </ul>
     * @author Kasper Schröder
     */
    @Test
    void testAddPlantToUserLibrary_InvalidRequestBody() {
        when(context.body()).thenReturn("invalid json");
        when(context.status(anyInt())).thenReturn(context);

        userService.addPlantToUserLibrary(context);

        verify(context).status(400);
        verify(context).result("Invalid request body");
    }
}
