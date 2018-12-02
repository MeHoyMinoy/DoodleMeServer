package com.hoymihoy.DoodleServer.DTOS;

import java.util.ArrayList;

public class Game {

    private Painting painting;
    private String gameName;
    private String currentPlayerUserName;
    private int currentPlayerSpot;
    private ArrayList<User> players;

    public Painting getPainting() {
        return painting;
    }

    public void setPainting(Painting painting) {
        this.painting = painting;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }
}
