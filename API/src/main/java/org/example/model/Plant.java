package org.example.model;

import java.sql.Date;

public class Plant {
    private String plantID;
    private String commonName;
    private String scientificName;
    private String familyName;
    private String imagePath;
    private int waterFrequency;

    public Plant(String plantID, String commonName, String scientificName, String familyName, String imagePath, int waterFrequency) {
        this.plantID = plantID;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.familyName = familyName;
        this.imagePath = imagePath;
        this.waterFrequency = waterFrequency;
    }

    // Getters and setters
    public String getPlantID() {
        return plantID;
    }
  
    public void setPlantID(String plantID) {
        this.plantID = plantID;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getWaterFrequency() {
        return waterFrequency;
    }

    public void setWaterFrequency(int waterFrequency) {
        this.waterFrequency = waterFrequency;
    }
}
