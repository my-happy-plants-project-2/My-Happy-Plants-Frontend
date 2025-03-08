package org.example.model;

import java.sql.Date;

public class UserPlant {
    private String plantID;
    private String nickname;
    private String owner;
    private Date lastWatered;
    private String note;
    private String species;
    private String commonName;
    private String family;


    private String category;
    private String imageUrl;
    private int lightReqs;
    private int waterFrequency;



    public UserPlant(String plantID, String nickname, String owner, Species species, Date lastWatered, String note) {
        this.plantID = plantID;
        this.nickname = nickname;
        this.owner = owner;
        this.species = species.getSpecies();
        this.lastWatered = lastWatered;
        this.note = note;
        this.commonName = species.getCommonName();
        this.family = species.getFamily();
        imageUrl = species.getImageUrl();
        lightReqs = species.getLightReqs();
        waterFrequency = species.getWaterFrequency();

    }


    // Getters and setters
    public String getSpecies() {
        return species;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getFamily() {
        return family;
    }

//    public String getCategory() {
//        return category;
//    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getLightReqs() {
        return lightReqs;
    }

    public int getWaterFrequency() {
        return waterFrequency;
    }
    public String getPlantID() {
        return plantID;
    }

    public void setPlantID(String plantID) {
        this.plantID = plantID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

//    public String getOwner() {
//        return owner;
//    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

//    public Species getSpecies() {
//        return species;
//    }

//    public void setSpecies(Species species) {
//        this.species = species;
//    }


    public Date getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(Date lastWatered) {
        this.lastWatered = lastWatered;
    }

//    public String getNote() {
//        return note;
//    }

    public void setNote(String note) {
        this.note = note;
    }

}
