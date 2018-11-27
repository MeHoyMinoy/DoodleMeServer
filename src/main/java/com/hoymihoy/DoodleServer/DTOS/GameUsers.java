package com.hoymihoy.DoodleServer.DTOS;

import java.util.ArrayList;

public class GameUsers {

    private int paintingID;
    private ArrayList<String> users;

    public int getPaintingID() {
        return paintingID;
    }

    public void setPaintingID(int paintingID) {
        this.paintingID = paintingID;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}
