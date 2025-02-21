package org.example.services;

import org.example.model.Plant;
import org.example.repositories.PlantRepository;

import java.util.List;

public class PlantService {
    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Plant> getAllPlants() {
        return plantRepository.getAllPlants();
    }
}
