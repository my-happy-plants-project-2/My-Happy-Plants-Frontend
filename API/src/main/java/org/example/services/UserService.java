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
 * Service class for handling user-related operations
 *
 * @author Kasper Schröder
 */
public class UserService {
    private final UserRepository userRepository;

    /**
     * Constructor for UserService
     * @param userRepository
     *
     * @author Kasper Schröder
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Adds a user to the database
     * @param context the context of the request made from a client
     *
     * @author Kasper Schröder
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
     * Verifies the login credentials of a user
     * @param context the context of the request made from a client
     *
     * @author Kasper Schröder
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
     * Deletes a user from the database
     * @param context the context of the request made from a client
     *
     * @author Kasper Schröder
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
     * Retrieves the plants owned by a user
     * @param context the context of the request made from a client
     *
     * @author Kasper Schröder
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
     * Deletes a plant from a user's library
     * @param context the context of the request made from a client
     *
     * @author Kasper Schröder
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
     * Waters a plant in a user's library
     * @param context the context of the request made from a client
     *
     * @author Kasper Schröder
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
