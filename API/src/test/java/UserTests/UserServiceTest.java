package UserTests;

import io.javalin.http.Context;
import org.example.model.User;
import org.example.repositories.UserRepository;
import org.example.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    // needs status to be added to context in UserService login method
    @Test
    void testLogin_Success() {
        User mockUser = new User("testuser", "test@example.com", "password123", 1);
        when(context.bodyAsClass(User.class)).thenReturn(mockUser);
        when(userRepository.checkLogin(anyString(), anyString())).thenReturn(true);
        when(context.status(anyInt())).thenReturn(context);

        userService.login(context);

        verify(context).status(200);
        verify(context).json(anyMap());
    }

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

    @Test
    void testLogin_Failure_MissingEmail() {
        User mockUser = new User("testuser", null, "password123", 1); // Email is null
        when(context.bodyAsClass(User.class)).thenReturn(mockUser);
        when(context.status(anyInt())).thenReturn(context);

        userService.login(context);

        verify(context).status(400);
        verify(context).result("Email and password must be provided");
    }

    @Test
    void testLogin_Failure_MissingPassword() {
        User mockUser = new User("testuser", "test@example.com", null, 1); // Password is null
        when(context.bodyAsClass(User.class)).thenReturn(mockUser);
        when(context.status(anyInt())).thenReturn(context);

        userService.login(context);

        verify(context).status(400);
        verify(context).result("Email and password must be provided");
    }
}
