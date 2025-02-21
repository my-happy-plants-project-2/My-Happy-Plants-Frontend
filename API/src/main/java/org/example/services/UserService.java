package org.example.services;

import io.javalin.http.Context;
import org.example.model.OwnedPlant;
import org.example.model.Plant;
import org.example.repositories.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void addUser(Context context) {
    }

    public void login(Context context) {
    }

    public void getUserByEmail(Context context) {
    }

    public boolean deleteUser(Context context) {
        return false;
    }

    public List<OwnedPlant> getUserPlants(Context context) {
        return null;
    }

    public boolean addPlantToUserLibrary(Context ctx) {
        return false;
    }

    public boolean deletePlantFromUserLibrary(Context ctx) {
        return false;
    }

    public boolean updatePlantInUserLibrary(Context ctx) {
        return false;
    }

    public boolean waterPlant(Context ctx) {
        return false;
    }
}
