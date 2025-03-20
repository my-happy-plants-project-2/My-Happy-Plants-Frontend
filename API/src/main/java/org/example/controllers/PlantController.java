package org.example.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.Species;
import org.example.services.PlantService;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * Controller class for API endpoints related to plants
 * Contains methods for handling plant-related requests
 *
 * @author Pehr Norten
 * @author Kasper Schröder
 */
public class PlantController {
    private static final String API_VERSION = "/api/v1";

    private final PlantService plantService;

    /**
     * Constructor for PlantController
     * @param plantService the plant service to be used for handling plant-related operations
     *
     * @author Pehr Norten
     */
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    /**
     * Registers routes for plant-related endpoints
     * @param app the Javalin application to register routes with
     *
     * @author Pehr Norten
     */
    public void registerRoutes(Javalin app) {
        app.get(API_VERSION + "/species", this::getAllPlants);
    }

    /**
     * Retrieves all plants from the database
     * @param context the context of the request made from a client
     *
     * @author Kasper Schröder
     */
    public void getAllPlants(@NotNull Context context) {
        plantService.getAllPlants(context);
    }
}
