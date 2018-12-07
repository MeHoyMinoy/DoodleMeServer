package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.PaintingUsers;
import com.hoymihoy.DoodleServer.DTOS.Painting;
import com.hoymihoy.DoodleServer.DTOS.User;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class DB_Paintings {

    DBConnector DBC = new DBConnector();

    public int createNewPainting(Painting p) throws SQLException
    {
        Blob convertedBlob = convertImageToBlob(p.getImage());
        //Blob convertedImage;
        p.setConvertedImage(convertedBlob);

        String updateString = "INSERT INTO Paintings(GameName, OwnerUserName, ConvertedImage, CurrentPlayerUserName, CurrentPlayerSpot) " +
                "VALUES('" + p.getGameName() + "', '" + p.getOwnerUserName() + "', '" + p.getConvertedImage() +"', '" + p.getCurrentPlayerUserName() + "', " + p.getCurrentPlayerSpot() + ")";
        try {
            DBC.con = DBC.initializeConnection();
            DBC.pstmt = DBC.con.prepareStatement(updateString, Statement.RETURN_GENERATED_KEYS);
            DBC.pstmt.executeUpdate();
            DBC.rs = DBC.pstmt.getGeneratedKeys();
            DBC.rs.next();
            int returnValue = DBC.rs.getInt(1);
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

    public Blob convertImageToBlob(String image){
        Blob b = null;
        try {
            b = DBC.initializeConnection().createBlob();
        } catch (SQLException E) {
            System.out.println(E);
        }

        //b = input.getBytes();
        byte[] buff = image.getBytes(StandardCharsets.UTF_8);
        try{
            assert b != null;
                b.setBytes(1, buff);
            }
        catch(Exception E){
            System.out.println(E);
            }

        try {
            DBC.initializeConnection().close();
        } catch (SQLException E) {
            System.out.println(E);
        }
        System.out.println(b);
        return b;
    }

    public String convertBlobToString(Blob blob){
        String s = "Boo";
        if(blob == null)
            return null;
        try {
            InputStream in = blob.getBinaryStream();
            int len = (int) blob.length(); //read as long
            long pos = 1; //indexing starts from 1
            byte[] bytes = blob.getBytes(pos, len);
            in.close();

            s = bytes.toString();
            System.out.println(s);
            return s;
        } catch (Exception E) {
            System.out.println(E);
        }
        return null;
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
                p.setImage(convertBlobToString(DBC.rs.getBlob("ConvertedImage")));
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
                "WHERE UP.userName COLLATE utf8_bin = '" + u + "'";

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
                p.setImage(convertBlobToString(DBC.rs.getBlob("ConvertedImage")));

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
