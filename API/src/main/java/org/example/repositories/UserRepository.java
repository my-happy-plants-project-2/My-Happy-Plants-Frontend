package org.example.repositories;

import org.example.model.UserPlant;
import org.example.services.IQueryExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

        try (ResultSet resultSet = queryExecutor.executeQuery("SELECT up.*, s.* FROM user_plants up JOIN species " +
                "s ON up.species = s.scientific_name WHERE owner = ?", userEmail)) {
            while (resultSet.next()) {
                userPlants.add(plantOwnerResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving user plants", e);
        }
        return userPlants;
    }

    /**
     * Extracts a UserPlant object from a result set
     * @param resultSet the result set to extract the UserPlant object from
     *
     * @author Kasper Schröder
     */
    private UserPlant plantOwnerResultSet(ResultSet resultSet) {
        try {
            String plantID = resultSet.getString("plant_id");
            String nickname = resultSet.getString("nickname");
            String owner = resultSet.getString("owner");
            String species = resultSet.getString("species");
            Date lastWatered = resultSet.getDate("last_watered");
            String note = resultSet.getString("note");

            return new UserPlant(plantID, nickname, owner, species, lastWatered, note);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error processing result set", e);
            return null;
        }
    }


    // TODO fix problem with resultset closing prematurely
    /**
     * Verifies the login credentials of a user
     * @return true if the login credentials are correct, false otherwise
     *
     * @author Kasper Schröder
     */
    public boolean checkLogin(String email, String password) {
        String query = "SELECT password FROM \"User\" WHERE email = ?";

        try (ResultSet resultSet = queryExecutor.executeQuery(query, email)) {
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString(1);
                return BCrypt.checkpw(password, hashedPassword);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during login check", e);
        }
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
            String query = "UPDATE owned_plants SET last_watered = CURRENT_DATE " +
                    "WHERE plant_id = ? AND user_id = ?";

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
    public boolean addUser(String email, String username, String password, int colorTheme) {
        try {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            queryExecutor.beginTransaction();
            String insertQuery = "INSERT INTO users (email, username, password, color_theme) VALUES (?, ?, ?, ?)";
            queryExecutor.executeUpdate(insertQuery, email, username, hashedPassword, colorTheme);
            queryExecutor.endTransaction();

            return true;
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid colorTheme format", e);
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding user to database", e);
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
    public boolean deleteAccount(String email, String password) {
        if (!checkLogin(email, password)) {
            LOGGER.log(Level.INFO, "Login failure");
            return false;
        }

        String querySelect = "SELECT id FROM \"User\" WHERE email = ?";
        String queryDeletePlants = "DELETE FROM \"plant\" WHERE user_id = ?";
        String queryDeleteUser = "DELETE FROM \"User\" WHERE id = ?";

        try {
            queryExecutor.beginTransaction();

            try (ResultSet resultSet = queryExecutor.executeQuery(querySelect, email)) {
                if (!resultSet.next()) {
                    throw new SQLException("User not found");
                }
                int id = resultSet.getInt("id");

                queryExecutor.executeUpdate(queryDeletePlants, id);
                queryExecutor.executeUpdate(queryDeleteUser, id);

                queryExecutor.endTransaction();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting account", e);
            queryExecutor.rollbackTransaction();
        }
        return false;
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
    public String addOwnerPlant(String nickname, String owner, String species) {
        try {
            String plantID = UUID.randomUUID().toString();

            queryExecutor.beginTransaction();
            String insertQuery = "INSERT INTO user_plants (plant_id, nickname, owner, species) VALUES (?, ?, ?, ?)";
            queryExecutor.executeUpdate(insertQuery, plantID, nickname, owner, species);
            queryExecutor.endTransaction();

            return plantID;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding user plant to database", e);
            return null;
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
            LOGGER.log(Level.SEVERE, "Error deleting user plant from database", e);
            return false;
        }
    }

    // TODO: Implement this method to update nickname and/or note of a users plant
    public boolean upDateOwnerPlant() {
        return false;
    }

}
