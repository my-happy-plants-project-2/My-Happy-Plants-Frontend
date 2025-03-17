package org.example.model;

public class Species {
    private String commonName;
    private String family;
    private String category;
    private String imageUrl;
    private int lightReqs;
    private int waterFrequency;
    private String description;
    private String species;


    public Species(String scientificName, String commonName, String family, String category, String imageUrl, int lightReqs, int waterFrequency, String description) {
        this.commonName = commonName;
        this.family = family;
        this.category = category;
        this.imageUrl = imageUrl;
        this.lightReqs = lightReqs;
        this.waterFrequency = waterFrequency;
        this.species = scientificName;
        this.description = description;
    }

    // Getters and setters


    public String getSpecies() {
        return species;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
