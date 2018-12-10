package com.hoymihoy.DoodleServer.DTOS;

import java.util.ArrayList;

public class Painting {
    private int paintingID;
    private String gameName;
    private String ownerUserName;
    private Image image;
    private String currentPlayerUserName;
    private int currentPlayerSpot;
    private int round;
    private ArrayList<String> players;


    public int getRound(){
        return round;
    }
    public void setRound(int round){
        this.round = round;
    }
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
