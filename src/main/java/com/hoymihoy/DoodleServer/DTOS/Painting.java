package com.hoymihoy.DoodleServer.DTOS;

import java.util.ArrayList;
import java.sql.Blob;
public class Painting {
    private int paintingID;
    private String gameName;
    private String ownerUserName;
    private String image;
    private Blob ConvertedImage;
    private String currentPlayerUserName;
    private int currentPlayerSpot;
    private ArrayList<String> players;

    public String getCurrentPlayerUserName() {
        return currentPlayerUserName;
    }

    public void setCurrentPlayerUserName(String currentPlayerUserName) {
        this.currentPlayerUserName = currentPlayerUserName;
    }

    public int getCurrentPlayerSpot() {
        return currentPlayerSpot;
    }

    public void setCurrentPlayerSpot(int currentPlayerSpot) {
        this.currentPlayerSpot = currentPlayerSpot;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public Blob getConvertedImage() {
        return ConvertedImage;
    }

    public void setConvertedImage(Blob convertedImage) {
        this.ConvertedImage = convertedImage;
    }

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
