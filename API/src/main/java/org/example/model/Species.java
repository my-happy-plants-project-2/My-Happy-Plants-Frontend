package org.example.model;

import java.util.Calendar;
import java.util.Date;

public class Species {
    private String scientificName;
    private String commonName;
    private String family;
    private String category;
    private String imageUrl;
    private int lightReqs;
    private int waterFrequency;
    private Date lastWatered = new Date();
    private String nickname = "";


    private String plantID = "";
    private String species;


    public Species(String scientificName, String commonName, String family, String category, String imageUrl, int lightReqs, int waterFrequency) {
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.family = family;
        this.category = category;
        this.imageUrl = imageUrl;
        this.lightReqs = lightReqs;
        this.waterFrequency = waterFrequency;
        this.species = scientificName;
    }

    // Getters and setters
    public Date getLastWatered() {
        return lastWatered;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPlantID() {
        return plantID;
    }

    public String getSpecies() {
        return species;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLightReqs() {
        return lightReqs;
    }

    public void setLightReqs(int lightReqs) {
        this.lightReqs = lightReqs;
    }

    public int getWaterFrequency() {
        return waterFrequency;
    }

    public void setWaterFrequency(int waterFrequency) {
        this.waterFrequency = waterFrequency;
    }
}
