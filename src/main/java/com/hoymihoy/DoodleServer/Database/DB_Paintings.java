package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.Painting;

import java.sql.SQLException;

public class DB_Paintings {

    DBConnector DBC = new DBConnector();

    public int createNewPainting(Painting p) throws SQLException
    {
        String updateString = "INSERT INTO Paintings(PaintingID, GameID, OwnerID, Image) " +
                "VALUES('" + p.getPaintingID() + "','" + p.getGameID() + "','" + p.getOwnerID() + "','" + p.getImage() +"');";
        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            int returnValue = DBC.stmt.executeUpdate(updateString);
            DBC.con.close();
            return returnValue;
        }
        catch (Exception e) {
            System.out.println(e);
            DBC.con.close();
            return -1;
        }
        finally {
            if (DBC.stmt != null)
            {DBC.stmt.close();}
        }
    }

    public Painting queryPaintingID(int paintingID) throws SQLException {
        String queryString = "SELECT * FROM Paintings WHERE PaintingID ='" + paintingID + "';";

        Painting p = new Painting();

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);

            while (DBC.rs.next())
            {
                p.setPaintingID(DBC.rs.getInt("PaintingID"));
                p.setGameID(DBC.rs.getInt("GameID"));
                p.setOwnerID(DBC.rs.getString("OwnerID"));
                p.setImage(DBC.rs.getString("Image"));
            }

            DBC.con.close();
            return p;

        }
        catch (Exception e) {
            System.out.println(e);
            p.setImage("NULL");
            DBC.con.close();
            return p;
        }
        finally {
            if (DBC.stmt != null)
            {DBC.stmt.close();}
        }
    }
}
