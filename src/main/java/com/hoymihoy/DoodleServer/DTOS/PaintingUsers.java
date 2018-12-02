package com.hoymihoy.DoodleServer.DTOS;

import java.util.ArrayList;

public class PaintingUsers {

    private int paintingID;
    private ArrayList<String> playerNames;

    public PaintingUsers(int paintingID, ArrayList<String> playerNames)
    {
        this.paintingID = paintingID;
        this.playerNames = playerNames;
    }

    public PaintingUsers(){}

    public int getPaintingID() {
        return paintingID;
    }

    public void setPaintingID(int paintingID) {
        this.paintingID = paintingID;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }
}
