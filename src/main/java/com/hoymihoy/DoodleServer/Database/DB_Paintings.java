package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.PaintingUsers;
import com.hoymihoy.DoodleServer.DTOS.Painting;
import com.hoymihoy.DoodleServer.DTOS.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB_Paintings {

    DBConnector DBC = new DBConnector();

    public int createNewPainting(Painting p) throws SQLException
    {
        //String convertedImage = convertImage(p.getImage());
        //p.setImage(convertedImage);

        String updateString = "INSERT INTO Paintings(GameName, OwnerUserName, Image, CurrentPlayerUserName, CurrentPlayerSpot) " +
                "VALUES('" + p.getGameName() + "', '" + p.getOwnerUserName() + "', '" + p.getImage() +"', '" + p.getCurrentPlayerUserName() + "', " + p.getCurrentPlayerSpot() + ")";
        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            int returnValue = DBC.stmt.executeUpdate(updateString, Statement.RETURN_GENERATED_KEYS);
            PaintingUsers pu = new PaintingUsers();
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
                p.setOwnerUserName(DBC.rs.getString("OwnerUserName"));
                p.setImage(DBC.rs.getString("Image"));
                p.setCurrentPlayerUserName(DBC.rs.getString("CurrentPlayerUserName"));
                p.setCurrentPlayerSpot(DBC.rs.getInt("CurrentPlayerSpot"));
            }

            DBC.con.close();
            return p;

        }
        catch (Exception e) {
            System.out.println(e);
            p.setImage(null);
            p.setGameName(null);
            p.setOwnerUserName(null);
            p.setPaintingID(-1);
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
        String updateString = "UPDATE Paintings SET" +
                " GameName = '" + p.getGameName() +
                "', Image = '" + p.getImage() +
                "' WHERE PaintingID = " + p.getPaintingID();

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

    public ArrayList<Painting> getUserPaintings(String u) throws SQLException
    {
        ArrayList<Painting> paintings = new ArrayList<>();

        String queryString = "SELECT * " +
                "FROM Paintings AS P " +
                "JOIN UserPaintings AS UP ON UP.PaintingID = P.PaintingID " +
                "WHERE UP.userName = '" + u + "'";

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);

            while (DBC.rs.next())
            {
                Painting p = new Painting();

                p.setPaintingID(DBC.rs.getInt("PaintingID"));
                p.setGameName(DBC.rs.getString("GameName"));
                p.setOwnerUserName(DBC.rs.getString("OwnerUserName"));
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

    public int createNewGame(PaintingUsers pu) throws SQLException
    {
        String updateString;
        int addedRows = 0;
        int paintingID = pu.getPaintingID();

        ArrayList<String> players = pu.getPlayerNames();

        for(int i = 0; i < players.size(); i++)
        {
            updateString = "INSERT INTO UserPaintings(PaintingID, UserName) " +
                    "VALUES(" + paintingID + ", '" + players.get(i) +"');";
            try {
                DBC.con = DBC.initializeConnection();
                DBC.stmt = DBC.con.createStatement();
                addedRows += DBC.stmt.executeUpdate(updateString);
                DBC.con.close();
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
        return addedRows;
    }
}
