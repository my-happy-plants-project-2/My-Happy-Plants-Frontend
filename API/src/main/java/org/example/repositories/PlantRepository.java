package org.example.repositories;

import org.example.model.Species;
import org.example.services.IQueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Repository class for handling plant-related database operations
 * Contains methods for retrieving plant data from the database
 *
 * @author Pehr Norten
 * @author Kasper Schröder
 */
public class PlantRepository {

    private static final Logger LOGGER = Logger.getLogger(PlantRepository.class.getName());
    private IQueryExecutor queryExecutor;

    /**
     * Constructor for PlantRepository
     * @param queryExecutor the query executor to be used for executing database operations
     *
     * @author Kasper Schröder
     */
    public PlantRepository(IQueryExecutor queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    /**
     * Retrieves all plants from the database
     * @return a list of Species objects
     *
     * @author Kasper Schröder
     */
    public List<Species> getAllSpecies() {
        List<Species> species = new ArrayList<>();
        List<Map<String,Object>> row = queryExecutor.executeQuery("SELECT * FROM species");
        if(!row.isEmpty()) {
            for(Map<String, Object> map : row) {
                String scientificName = map.get("scientific_name").toString();
                String family = map.get("family").toString();
                String commonName = map.get("common_name").toString();
                String category = map.get("category").toString();
                String imageUrl = map.get("image_url").toString();
                int lightReqs = Integer.parseInt(map.get("light_reqs").toString());
                int waterFrequency = Integer.parseInt(map.get("waterfrequency").toString());
                String description = map.get("description") !=null ? map.get("description").toString(): "";
                String caring = map.get("caring") !=null ? map.get("caring").toString(): "";
                species.add(new Species(scientificName, commonName, family, category, imageUrl, lightReqs, waterFrequency, description, caring));
            }
            return  species;
        }
        LOGGER.log(Level.SEVERE, "Error retrieving plants from database");
        return species;
    }
}
