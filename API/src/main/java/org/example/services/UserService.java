package org.example.services;

import io.javalin.http.Context;
import org.example.model.JWTUtil;
import org.example.model.UserPlant;
import org.example.repositories.UserRepository;

import java.util.List;
import java.util.Map;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(Context context) {
        if (userRepository.addUser(context)) {
            context.status(201).result("User created successfully");
        } else {
            context.status(500).result("Error creating user");
        }
    }

    public void login(Context context) {
        String email = context.formParam("email");
        String password = context.formParam("password");

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

    public boolean deleteUser(Context context) {
        String email = context.formParam("email");
        String password = context.formParam("password");
        if (userRepository.deleteAccount(email, password)) {
            context.status(200).result("User deleted successfully");
            return true;
        } else {
            context.status(500).result("Error deleting user");
            return false;
        }
    }

    public List<UserPlant> getUserPlants(Context context) {
        return userRepository.getUserPlants(context);
    }

    public boolean addPlantToUserLibrary(Context context) {
        if (userRepository.addOwnerPlant(context)) {
            context.status(201).result("Plant added to user library");
            return true;
        } else {
            context.status(500).result("Error adding plant to user library");
            return false;
        }
    }

    public boolean deletePlantFromUserLibrary(Context context) {
        if (userRepository.deleteOwnerPlant(context)) {
            context.status(200).result("Plant deleted from user library");
            return true;
        } else {
            context.status(500).result("Error deleting plant from user library");
            return false;
        }
    }

    public boolean updatePlantInUserLibrary(Context ctx) {
        // Implement logic to update a plant in the user's library
        return false;
    }

    public boolean waterPlant(Context ctx) {
        if (userRepository.waterPlant(ctx)) {
            ctx.status(200).result("Plant watered successfully");
            return true;
        } else {
            ctx.status(500).result("Error watering plant");
            return false;
        }
    }}
