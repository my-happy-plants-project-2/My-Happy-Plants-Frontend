package org.example;
import org.example.config.AppConfig;
import org.example.controllers.PlantController;
import org.example.controllers.UserController;
import org.example.repositories.PlantRepository;
import org.example.repositories.UserRepository;
import org.example.services.PlantService;
import org.example.services.UserService;

public class Main {
    public static void main(String[] args) {

        // Create instances of the PlantController, PlantService, PlantRepository.
        PlantRepository plantRepository = new PlantRepository();
        PlantService plantService = new PlantService(plantRepository);
        PlantController plantController = new PlantController(plantService);

        // Create instances of the UserController, UserService, UserRepository.
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        // Start the server
        AppConfig.configureApp(plantController, userController).start(8080);
    }
}