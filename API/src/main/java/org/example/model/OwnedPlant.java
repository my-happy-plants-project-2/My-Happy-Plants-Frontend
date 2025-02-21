package org.example.model;

public class OwnedPlant extends Plant {
    private String nickname;
    private String lastWatered; // ska vara SQLDate
    private String ownersEmail;

    public OwnedPlant(String plantID, String commonName, String scientificName, String familyName, String imagePath, int waterFrequency, String nickname, String lastWatered, String ownersEmail) {
        super(plantID, commonName, scientificName, familyName, imagePath, waterFrequency);
        this.nickname = nickname;
        this.lastWatered = lastWatered;
        this.ownersEmail = ownersEmail;
    }

    // Getters and setters
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(String lastWatered) {
        this.lastWatered = lastWatered;
    }

    public String getOwnersEmail() {
        return ownersEmail;
    }

    public void setOwnersEmail(String ownersEmail) {
        this.ownersEmail = ownersEmail;
    }
}
