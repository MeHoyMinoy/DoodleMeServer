package com.hoymihoy.DoodleServer.DTOS;

public class Painting {
    private int paintingID;
    private String gameName;
    private String ownerUserName;
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

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
