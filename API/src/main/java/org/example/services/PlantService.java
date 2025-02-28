package org.example.services;

import org.example.model.Species;
import org.example.repositories.PlantRepository;

import io.javalin.http.Context;

import java.util.List;

public class PlantService {
    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public void getAllPlants(Context context) {
        List<Species> species = plantRepository.getAllSpecies();

        if (species.isEmpty()) {
            context.status(200).json("[]");
        } else {
            context.json(species);
        }
    }
}
