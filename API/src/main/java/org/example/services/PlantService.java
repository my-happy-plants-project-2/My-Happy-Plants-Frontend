package org.example.services;

import org.example.model.Species;
import org.example.repositories.PlantRepository;

import java.util.List;

public class PlantService {
    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Species> getAllPlants() {
        return plantRepository.getAllSpecies();
    }
}
