package org.example.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.example.model.JWTUtil;
import org.example.model.User;
import org.example.model.UserPlant;
import org.example.repositories.UserRepository;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Service class responsible for handling user-related operations,
 * including authentication, user management, and user-plant interactions.
 * It serves as a bridge between the HTTP request context and the underlying
 * database repository layer.
 * <p>
 * This class interacts with {@link UserRepository} to perform database operations
 * and uses JWT authentication for securing API endpoints.
 *
 * @author Kasper Schröder
 * @author Pehr Norten
 * @author Christian Storck
 * @author Ida Nordenswan
 */
public class UserService {
    private final UserRepository userRepository;

    /**
     * Constructs a new UserService instance with the provided {@link UserRepository}.
     *
     * @param userRepository the repository responsible for user data management.
     *
     * @author Pehr Norten
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user in the system by storing their details in the database.
     * The password is securely hashed before being stored.
     *
     * @param context the HTTP request context containing user details in the request body.
     * The body must contain a valid JSON representation of a {@link User}.
     *
     * @author Kasper Schröder
     * @author Christian Storck
     */
    public void addUser(Context context) {
        User user = context.bodyAsClass(User.class);

        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        int colorTheme = user.getColorTheme();

        if (userRepository.addUser(email, username, password, String.valueOf(colorTheme))) {
            context.status(201).result("User created successfully");
        } else {
            context.status(500).result("Error creating user");
        }
    }

    /**
     * Authenticates a user by verifying their email and password.
     * If successful, a JWT token is generated and returned.
     *
     * @param context the HTTP request context containing login credentials in the request body.
     * The body must contain a valid JSON representation of a {@link User}.
     *
     * @author Kasper Schröder
     * @author Christian Storck
     */
    public void login(Context context) {
        User user = context.bodyAsClass(User.class);

        String email = user.getEmail();
        String password = user.getPassword();

        if (email == null || password == null) {
            context.status(400).result("Email and password must be provided");
            return;
        }

        if (userRepository.checkLogin(email, password)) {
            String token = JWTUtil.generateToken(email);
            context.json(Map.of("token", token));
        } else {
            context.status(401).result("Invalid email or password");
        }
    }

    /**
     * Retrieves all plants owned by the authenticated user.
     *
     * @param context the request context containing the authorization token.
     *
     * @author Kasper Schröder
     * @author Johnny Rosenquist
     */
    public void deleteUser(Context context) {
        String email = getEmailFromToken(context);
        System.out.println("deleteUser");
        if (userRepository.deleteAccount(email)) {
            context.status(200).result("User deleted successfully");
        } else {
            context.status(500).result("Error deleting user");
        }
    }

    /**
     * Adds a plant to the authenticated user's library.
     *
     * @param context the request context containing plant data.
     *
     * @author Kasper Schröder
     * @author Johnny Rosenquist
     */
    public void getUserPlants(Context context) {
        String email = getEmailFromToken(context);
        try {
            List<UserPlant> userPlants = userRepository.getUserPlants(email);
            context.json(userPlants);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error retrieving user plants - get user plants");
            context.status(500).result("Error retrieving user plants");
        }
    }

    /**
     * Adds a plant to a user's library
     * @param context the context of the request made from a client
     *
     * @author Kasper Schröder
     * @author Johnny Rosenquist
     */
    public void addPlantToUserLibrary(Context context) {
        String plantID = "";
        String species = "";
        String nickname = "";
        String owner = "";
        String note = "";
        String description = "";

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(context.body());
            plantID = node.get("plant_id").toString().replaceAll("\"", "");
            species = node.get("species").toString().replaceAll("\"", "");
            nickname = node.get("nickname").toString().replaceAll("\"", "");
            owner = getEmailFromToken(context);
            description = node.has("description") ? node.get("description").asText() : "";
        }
        catch (Exception e) {
            e.printStackTrace();
            context.status(400).result("Invalid request body");
            return;
        }

        if (userRepository.addOwnerPlant(plantID, nickname, owner, note, species,description)) {
            context.status(201).result("Plant added to user library");
        } else {
            context.status(500).result("Error adding plant to user library");
        }
    }

    /**
     * Deletes a plant from the authenticated user's library.
     *
     * @param context the request context containing the plant ID.
     *
     * @author Kasper Schröder
     * @author Johnny Rosenquist
     */
    public void deletePlantFromUserLibrary(Context context) {
        String plantID = context.pathParam("id");

        if (userRepository.deleteOwnerPlant(plantID)) {
            context.status(200).result("Plant deleted from user library");
        } else {
            context.status(500).result("Error deleting plant from user library");
        }
    }

    /**
     * Marks a plant as watered in the authenticated user's library.
     *
     * @param context the request context containing the plant ID.
     *
     * @author Johnny Rosenquist
     */
    public void waterPlant(Context context) {
        String plantID = context.pathParam("id");
        String email = getEmailFromToken(context);

        if (userRepository.waterPlant(plantID, email)) {
            context.status(200).result("Plant watered successfully");
        } else {
            context.status(500).result("Error watering plant");
        }
    }

    /**
     * Changes the nickname of a plant in the authenticated user's library.
     *
     * @param context the request context containing the new nickname and plant ID.
     *
     * @author Johnny Rosenquist
     */
    public void changeNickname(Context context) {
        String plantID = context.pathParam("id");
        String nickname = "";
        String email = getEmailFromToken(context);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(context.body());
            nickname = node.get("nickname").toString().replaceAll("\"", "");
        }
        catch (Exception e) {
            context.status(400).result("Invalid request body");
            return;
        }

        if (userRepository.changeNickname(plantID, nickname, email)) {
            context.status(200).result("Nickname changed successfully");
        } else {
            context.status(500).result("Error changing nickname");
        }
    }

    /**
     * Extracts the email of a user from the JWT token included in the request header.
     *
     * @param context the request context containing the authorization token.
     * @return the email address of the authenticated user, or an empty string if extraction fails.
     *
     * @author Johnny Rosenquist
     */
    public String getEmailFromToken(Context context) {
        String tokenString = context.header("Authorization").substring(7);
        ObjectMapper mapper = new ObjectMapper();
        String email = "";
        try {
            JsonNode node = mapper.readTree(Base64.getDecoder().decode(JWT.decode(tokenString).getPayload()));
            email = node.get("sub").toString().replaceAll("\"", "");
        }
        catch (Exception e) {
            context.status(500).result("Error authenticating user token");
        }
        return email;
    }
}
