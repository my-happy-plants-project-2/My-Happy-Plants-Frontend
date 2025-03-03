package org.example.services;

import io.javalin.http.Context;
import org.example.model.JWTUtil;
import org.example.model.User;
import org.example.model.UserPlant;
import org.example.repositories.UserRepository;

import java.util.List;
import java.util.Map;

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

        String email = user.getEmail();
        String username = user.getUserName();
        String password = user.getPassword();
        int colorTheme = user.getColorTheme();

        if (userRepository.addUser(email, username, password, colorTheme)) {
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
        User user = context.bodyAsClass(User.class);

        String email = user.getEmail();
        String password = user.getPassword();

        if (userRepository.deleteAccount(email, password)) {
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
        try {
            User user = context.bodyAsClass(User.class);
            String email = user.getEmail();

            List<UserPlant> userPlants = userRepository.getUserPlants(email);
            context.json(userPlants);
        } catch (Exception e) {
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
        UserPlant userPlant = context.bodyAsClass(UserPlant.class);

        String nickname = userPlant.getNickname();
        String owner = userPlant.getOwner();
        String species = userPlant.getSpecies();

        String plantID = userRepository.addOwnerPlant(nickname, owner, species);
        if (plantID != null) {
            context.status(201).result("Plant added to user library").result(plantID);
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
        String plantID = context.pathParam("plant_id");

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
        String plantID = context.pathParam("plant_id");
        String userId = context.cookie("user_id");

        if (userRepository.waterPlant(plantID, userId)) {
            context.status(200).result("Plant watered successfully");
        } else {
            context.status(500).result("Error watering plant");
        }
    }
}
