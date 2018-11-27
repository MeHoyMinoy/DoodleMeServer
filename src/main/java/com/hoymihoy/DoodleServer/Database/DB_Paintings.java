package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.Painting;
import com.hoymihoy.DoodleServer.DTOS.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class DB_Paintings {

    DBConnector DBC = new DBConnector();

    public int createNewPainting(Painting p) throws SQLException
    {
        String updateString = "INSERT INTO Paintings(PaintingID, GameID, GameName, OwnerUserID, Image) " +
                "VALUES(" + p.getPaintingID() + ", '" + p.getGameName() + "', " + p.getOwnerUserID() + ", '" + p.getImage() +"');";
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
                p.setGameName(DBC.rs.getString("GameName"));
                p.setOwnerUserID(DBC.rs.getString("OwnerUserID"));
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

    public int updatePainting(Painting p) throws SQLException
    {
        String updateString = "UPDATE Paintings SET " +
                "GameName = " + p.getGameName() +
                "Image = " + p.getImage() +
                "WHERE PaintingID = " + p.getPaintingID();

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

    public ArrayList<Painting> getUserPaintings(User u) throws SQLException
    {
        ArrayList<Painting> paintings = new ArrayList<>();

        String queryString = "SELECT * " +
                "FROM Paintings AS P" +
                "JOIN UserPaintings AS UP ON UP.PaintingID = P.PaintingID" +
                "WHERE UP.UserID = " + u.getUserID();

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);

            while (DBC.rs.next())
            {
                Painting p = new Painting();

                p.setPaintingID(DBC.rs.getInt("PaintingID"));
                p.setGameName(DBC.rs.getString("GameName"));
                p.setOwnerUserID(DBC.rs.getString("OwnerUserID"));
                p.setImage(DBC.rs.getString("Image"));

                paintings.add(p);
            }
            DBC.con.close();
            return paintings;

        }
        catch (Exception e) {
            System.out.println(e);
            DBC.con.close();
            return paintings;
        }
        finally {
            if (DBC.stmt != null)
            {DBC.stmt.close();}
        }
    }

}
