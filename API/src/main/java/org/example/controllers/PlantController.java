package org.example.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.model.Species;
import org.example.services.PlantService;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class PlantController {
    private static final String API_VERSION = "/api/v1";

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    public void registerRoutes(Javalin app) {
        app.get(API_VERSION + "/species", this::getAllPlants);
    }

    public void getAllPlants(@NotNull Context ctx) {
        List<Species> species = plantService.getAllPlants();

        if (species.isEmpty()) {
            ctx.status(200).json("[]");
        } else {
            ctx.json(species);
        }
    }
}
