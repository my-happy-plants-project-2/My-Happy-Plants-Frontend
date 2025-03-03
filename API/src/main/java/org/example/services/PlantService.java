package org.example.services;

import org.example.model.Species;
import org.example.repositories.PlantRepository;

import io.javalin.http.Context;

import java.util.List;

/**
 * Service class for handling plant-related operations
 * Contains methods for retrieving plant data
 * from the database
 *
 * @author Pehr Norten
 * @author Kasper Schröder
 */
public class PlantService {
    private final PlantRepository plantRepository;

    /**
     * Constructor for PlantService
     * @param plantRepository the plant repository to be used for database operations
     *
     * @author Pehr Norten
     */
    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    /**
     * Retrieves all plants from the database
     * @param context the context of the request made from a client
     *
     * @author Kasper Schröder
     */
    public void getAllPlants(Context context) {
        List<Species> species = plantRepository.getAllSpecies();

        if (species.isEmpty()) {
            context.status(200).json(species);
        } else {
            context.json(species);
        }
    }
}
