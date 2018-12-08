package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.Image;
import com.hoymihoy.DoodleServer.DTOS.PaintingUsers;
import com.hoymihoy.DoodleServer.DTOS.Painting;
import com.hoymihoy.DoodleServer.DTOS.User;

import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class DB_Paintings {

    DBConnector DBC = new DBConnector();

    public int createNewPainting(Painting p) throws SQLException
    {

        String updateString = "INSERT INTO Paintings(GameName, OwnerUserName, CurrentPlayerUserName, CurrentPlayerSpot) " +
                "VALUES('" + p.getGameName() + "', '" + p.getOwnerUserName() + "', '" + p.getCurrentPlayerUserName() + "', " + p.getCurrentPlayerSpot() + ")";
        try {
            DBC.con = DBC.initializeConnection();
            DBC.pstmt = DBC.con.prepareStatement(updateString, Statement.RETURN_GENERATED_KEYS);
            DBC.pstmt.executeUpdate();
            DBC.rs = DBC.pstmt.getGeneratedKeys();
            DBC.rs.next();
            int returnValue = DBC.rs.getInt(1);
            String pathName = returnValue + ".txt";
            try {
                File file = new File(pathName);
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(p.getImage());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String updateString2 = "UPDATE Paintings SET " +
                    "ImagePath = '" + pathName + "' WHERE PaintingID = " + returnValue;

            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.stmt.executeUpdate(updateString2);

            PaintingUsers pu = new PaintingUsers(returnValue, p.getPlayers());
            createNewGame(pu);
            DBC.con.close();
            return returnValue;
        }
        catch (Exception e) {
            System.out.println(e);
            DBC.con.close();
            return -1;
        }
        finally {
            if (DBC.pstmt != null)
            {DBC.pstmt.close();}
        }
    }

    public Painting queryPaintingID(int paintingID) throws SQLException {
        String queryString = "SELECT * FROM Paintings WHERE PaintingID ='" + paintingID + "';";

        Painting p = new Painting();
        //Image image = new Image();

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);

            while (DBC.rs.next())
            {
                p.setPaintingID(DBC.rs.getInt("PaintingID"));
                p.setGameName(DBC.rs.getString("GameName"));
                p.setOwnerUserName(DBC.rs.getString("OwnerUserName"));
                p.setImage(getImageData(DBC.rs.getString("ImagePath")));
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
                "', ImagePath = '" + p.getImage() +
                "', CurrentPlayerSpot = '" + p.getCurrentPlayerSpot() +
                "', CurrentPlayerUserName = '" + p.getCurrentPlayerSpot() +
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
                "WHERE UP.userName COLLATE utf8_bin = '" + u + "'";

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);

            while (DBC.rs.next())
            {
                Painting p = new Painting();
                Image image = new Image();

                p.setPaintingID(DBC.rs.getInt("PaintingID"));
                p.setGameName(DBC.rs.getString("GameName"));
                p.setOwnerUserName(DBC.rs.getString("OwnerUserName"));
                p.setImage(getImageData(DBC.rs.getString("ImagePath")));
                p.setCurrentPlayerUserName(DBC.rs.getString("CurrentPlayerUserName"));
                p.setCurrentPlayerSpot(DBC.rs.getInt("CurrentPlayerSpot"));

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

    public static String getImageData(String fileName)
    {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }


}
