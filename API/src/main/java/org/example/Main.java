package org.example;
import org.example.config.AppConfig;
import org.example.config.ISQLConfig;
import org.example.config.SQLConfig;
import org.example.controllers.PlantController;
import org.example.controllers.UserController;
import org.example.repositories.PlantRepository;
import org.example.repositories.UserRepository;
import org.example.services.IQueryExecutor;
import org.example.services.PlantService;
import org.example.services.QueryExecutor;
import org.example.services.UserService;

public class Main {
    public static void main(String[] args) {
        ISQLConfig sqlConfig = new SQLConfig();
        Runtime.getRuntime().addShutdownHook(new Thread(sqlConfig::closeConnection));

        IQueryExecutor queryExecutor = new QueryExecutor(sqlConfig);


        // Create instances of the PlantController, PlantService, PlantRepository.
        PlantRepository plantRepository = new PlantRepository(queryExecutor);
        PlantService plantService = new PlantService(plantRepository);
        PlantController plantController = new PlantController(plantService);

        // Create instances of the UserController, UserService, UserRepository.
        UserRepository userRepository = new UserRepository(queryExecutor);
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        // Start the server
        AppConfig.configureApp(plantController, userController).start(8080);


    }
}