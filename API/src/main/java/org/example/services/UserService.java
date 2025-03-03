package org.example.services;

import io.javalin.http.Context;
import org.example.model.JWTUtil;
import org.example.model.User;
import org.example.model.UserPlant;
import org.example.repositories.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public void deleteUser(Context context) {
        String email = context.formParam("email");
        String password = context.formParam("password");

        if (userRepository.deleteAccount(email, password)) {
            context.status(200).result("User deleted successfully");
        } else {
            context.status(500).result("Error deleting user");
        }
    }

    public void getUserPlants(Context context) {
        try {
            String email = context.pathParam("email");
            List<UserPlant> userPlants = userRepository.getUserPlants(email);
            context.json(userPlants);
        } catch (Exception e) {
            context.status(500).result("Error retrieving user plants");
        }
    }

    public void addPlantToUserLibrary(Context context) {
        String plantID = context.formParam("plant_id");
        String nickname = context.formParam("nickname");
        String owner = context.cookie("user_id");
        String note = context.formParam("note");

        if (userRepository.addOwnerPlant(plantID, nickname, owner, note)) {
            context.status(201).result("Plant added to user library");
        } else {
            context.status(500).result("Error adding plant to user library");
        }
    }

    public void deletePlantFromUserLibrary(Context context) {
        String plantID = context.pathParam("plant_id");

        if (userRepository.deleteOwnerPlant(plantID)) {
            context.status(200).result("Plant deleted from user library");
        } else {
            context.status(500).result("Error deleting plant from user library");
        }
    }

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
