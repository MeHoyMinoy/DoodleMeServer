package com.hoymihoy.DoodleServer.DTOS;

public class Painting {
    private int PaintingID;
    private int GameID;
    private String OwnerID;
    private String Image;

    public int getPaintingID() {
        return PaintingID;
    }

    public void setPaintingID(int paintingID) {
        PaintingID = paintingID;
    }

    public int getGameID() {
        return GameID;
    }

    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public String getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(String ownerID) {
        OwnerID = ownerID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
