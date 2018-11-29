package com.hoymihoy.DoodleServer.DTOS;

public class Painting {
    private int paintingID;
    private String gameName;
    private int ownerUserID;
    private String image;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getPaintingID() {
        return paintingID;
    }

    public void setPaintingID(int paintingID) {
        this.paintingID = paintingID;
    }

    public int getOwnerUserID() {
        return ownerUserID;
    }

    public void setOwnerUserID(int ownerUserID) {
        this.ownerUserID = ownerUserID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
