package org.example.model;

import java.sql.Date;

public class UserPlant {
    private String plantID;
    private String nickname;
    private String owner;
    private String species;
    private Date lastWatered;
    private String note;


    public UserPlant(String plantID, String nickname, String owner, String species, Date lastWatered, String note) {
        this.plantID = plantID;
        this.nickname = nickname;
        this.owner = owner;
        this.species = species;
        this.lastWatered = lastWatered;
        this.note = note;
    }


    // Getters and setters
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }


    public Date getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(Date lastWatered) {
        this.lastWatered = lastWatered;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
