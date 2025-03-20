package org.example.repositories;

import io.javalin.http.Context;
import org.example.model.Species;
import org.example.model.UserPlant;
import org.example.services.IQueryExecutor;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Repository class for handling user-related database operations
 *
 * @author Kasper Schröder
 */
public class UserRepository {
    private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());
    private IQueryExecutor queryExecutor;

    /**
     * Constructor for UserRepository
     * @param queryExecutor the query executor to be used for executing database operations
     *
     * @author Kasper Schröder
     */
    public UserRepository(IQueryExecutor queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    /**
     * Retrieves all user plants from the database based on the user's email
     * @param userEmail the email of the user
     * @return a list of UserPlant objects
     *
     * @author Kasper Schröder
     */
    public List<UserPlant> getUserPlants(String userEmail) {
        List<UserPlant> userPlants = new ArrayList<>();
        String query = "SELECT * FROM user_plants JOIN species ON user_plants.species = species.scientific_name WHERE owner = ?";
        List<Map<String, Object>> plantList = queryExecutor.executeQuery(query, userEmail);
        for (Map map : plantList) {
            userPlants.add(plantOwnerResultSet(map));
        }
        if (plantList.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Error retrieving user plants");
        }
        return userPlants;
    }

    /**
     * Extracts a UserPlant object from a result set
     * @param resultSet the result set to extract the UserPlant object from
     *
     * @author Kasper Schröder
     */
    private UserPlant plantOwnerResultSet(Map<String, Object> resultSet) {
        if (!resultSet.isEmpty()) {
            String plantID = resultSet.get("plant_id").toString();
            String nickname = resultSet.get("nickname").toString();
            String owner = resultSet.get("owner").toString();
            Date lastWatered = (Date) resultSet.get("last_watered");
            String note = resultSet.get("note").toString();
            String scientificName = resultSet.get("scientific_name").toString();
            String commonName = resultSet.get("common_name").toString();
            String family = resultSet.get("family").toString();
            String category = resultSet.get("category").toString();
            String imageUrl = resultSet.get("image_url").toString();
            int lightReqs = Integer.parseInt(resultSet.get("light_reqs").toString());
            int waterFrequency = Integer.parseInt(resultSet.get("waterfrequency").toString());
            String description = resultSet.get("description").toString();
            String caring = resultSet.get("caring").toString();

            Species species = new Species(scientificName, commonName, family, category, imageUrl, lightReqs, waterFrequency, description, caring);

            return new UserPlant(plantID, nickname, owner, species, lastWatered, note);
        }
        LOGGER.log(Level.SEVERE, "Error processing result set");
        return null;
    }


    // TODO fix problem with resultset closing prematurely
    /**
     * Verifies the login credentials of a user
     * @return true if the login credentials are correct, false otherwise
     *
     * @author Kasper Schröder
     */
    public boolean checkLogin(String email, String password) {
        String query = "SELECT password FROM \"users\" WHERE email = ?";
        List<Map<String, Object>> results = queryExecutor.executeQuery(query, email);
        if (!results.isEmpty()) {
            String hashedPassword = results.getFirst().get("password").toString();
            return BCrypt.checkpw(password, hashedPassword);
        }
        LOGGER.log(Level.INFO, "Invalid login credentials");

        return false;
    }

    /**
     * Waters a plant in the user's library
     * @param plantID the ID of the plant to water
     * @param userId the ID of the user watering the plant
     * @return true if the plant was watered successfully, false otherwise
     *
     * @author Kasper Schröder
     */
    public boolean waterPlant(String plantID, String userId) {
        try {
            String query = "UPDATE user_plants SET last_watered = CURRENT_DATE " +
                    "WHERE plant_id = ? AND owner = ?";

            queryExecutor.beginTransaction();
            queryExecutor.executeUpdate(query, plantID, userId);
            queryExecutor.endTransaction();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error watering plant", e);
            queryExecutor.rollbackTransaction();
            return false;
        }
    }

    public boolean changeNickname(String plantID, String nickname, String username) {
        try {
            String query = "UPDATE user_plants SET nickname = ? WHERE plant_id = ? AND owner = ?";
            queryExecutor.beginTransaction();
            queryExecutor.executeUpdate(query, nickname, plantID, username);
            queryExecutor.endTransaction();
            return true;
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error changing nickname", e);
            queryExecutor.rollbackTransaction();
            return false;
        }
    }

    /**
     * Adds a user to the database
     * @param email the email of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param colorTheme the color theme of the user
     * @return true if the user was added successfully, false otherwise
     *
     * @author Kasper Schröder
     */
    public boolean addUser(String email, String username, String password, String colorTheme) {
        try {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            int colorThemeInt = Integer.parseInt(colorTheme);

            queryExecutor.beginTransaction();
            String insertQuery = "INSERT INTO users (email, username, password, color_theme) VALUES (?, ?, ?, ?)";
            if (!queryExecutor.executeUpdate(insertQuery, email, username, hashedPassword, colorThemeInt)) {
                throw new Exception();
            };
            queryExecutor.endTransaction();
            return true;
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid colorTheme format", e);
            queryExecutor.rollbackTransaction();
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding user to database", e);
            queryExecutor.rollbackTransaction();
            return false;
        }
    }

    /**
     * Deletes a user from the database
     * @param email the email of the user
     * @param password the password of the user
     * @return true if the user was deleted successfully, false otherwise
     *
     * @author Kasper Schröder
     */
    public boolean deleteAccount(String email) {

        String queryDeletePlants = "DELETE FROM user_plants WHERE owner = ?";
        String queryDeleteUser = "DELETE FROM users WHERE email = ?";

        try {
            queryExecutor.beginTransaction();
            queryExecutor.executeUpdate(queryDeletePlants, email);
            queryExecutor.executeUpdate(queryDeleteUser, email);
            queryExecutor.endTransaction();
            return true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting account", e);
            return false;
        }
    }

    /**
     * Adds a plant to the user's library
     * @param nickname the nickname of the plant
     * @param owner the owner of the plant
     * @param species the species of the plant
     * @return the ID of the plant if it was added successfully, null otherwise
     *
     * @author Kasper Schröder
     */
    public boolean addOwnerPlant(String plantID, String nickname, String owner, String note, String species, String description) {
        try {
            queryExecutor.beginTransaction();
            Date lastWatered = Date.valueOf(LocalDate.now());
            String insertQuery = "INSERT INTO user_plants (plant_id, nickname, owner, note, species, last_watered) VALUES (?, ?, ?, ?, ?, ?)";
            queryExecutor.executeUpdate(insertQuery, plantID, nickname, owner, note, species, lastWatered);
//            String updateSpeciesQuery = "UPDATE species SET description = ? WHERE scientific_name = ?";
//            queryExecutor.executeUpdate(updateSpeciesQuery, description,species);

            queryExecutor.endTransaction();

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding user plant to database", e);
            queryExecutor.rollbackTransaction();
            return false;
        }
    }

    /**
     * Deletes a plant from the user's library
     * @param plantID the ID of the plant to delete
     * @return true if the plant was deleted successfully, false otherwise
     *
     * @author Kasper Schröder
     */
    public boolean deleteOwnerPlant(String plantID) {
        try {
            queryExecutor.beginTransaction();
            String deleteQuery = "DELETE FROM user_plants WHERE plant_id = ?";
            queryExecutor.executeUpdate(deleteQuery, plantID);
            queryExecutor.endTransaction();

            return true;
        } catch (Exception e) {
            queryExecutor.rollbackTransaction();
            LOGGER.log(Level.SEVERE, "Error deleting user plant from database", e);
            return false;
        }
    }
}
