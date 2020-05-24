package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.SecureUserLogin;
import com.hoymihoy.DoodleServer.DTOS.User;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

import java.sql.*;

public class DBConnector {
    public Connection con;
    public Statement stmt;
    public ResultSet rs;
    public PreparedStatement pstmt;
    private String hostName = "doodle-me-server.database.windows.net";
    private String DBName = "DoodleMeDB";
    private String DBUsername = "DoodleMeAdmin";//""admin";
    private String DBPassword = "DoodleMe09";//"DoodleMe";
    private String jdbcUrl = String.format(
            "jdbc:sqlserver://doodle-me-server.database.windows.net:1433;database=DoodleMeDB;user=DoodleMeAdmin@doodle-me-server;password=" +
                    "%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", DBPassword);




    protected Connection initializeConnection() throws SQLException {
        try{
            con = DriverManager.getConnection(jdbcUrl);
            String schema = con.getSchema();
            System.out.println("Successful connection - Schema: " + schema);
            stmt=con.createStatement();
        } catch (Exception ex){
            System.out.println(ex);
            con.close();
            return con;
        }

        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }
    
    public int dropAllTables() throws SQLException {
        try {
            con = initializeConnection();
            
            stmt = con.createStatement();
            
            //stmt.executeUpdate("DROP TABLE Users");

            //stmt.executeUpdate("DROP TABLE FriendsList");

            //stmt.executeUpdate("DROP TABLE Paintings");

            //stmt.executeUpdate("DROP TABLE UserPaintings");
        } 
        catch (Exception ex) {
            System.out.println(ex);
            con.close();
            return -1;
        }
        finally {
            if (stmt != null) {stmt.close();}
        }
        con.close();
        return 1;
    }
    
    public int createTables() throws SQLException {
        
        try {
            con = initializeConnection();

            stmt=con.createStatement();
            
            stmt.executeUpdate("create TABLE Users(" +
                    "userName VARCHAR(100) NOT NULL PRIMARY KEY, " +
                    "password VARCHAR(100) NOT NULL, " +
                    "firstName VARCHAR(100), " +
                    "lastName VARCHAR(100), " +
                    "nickName VARCHAR(100), " +
                    "birthDate DATE)");

            stmt.executeUpdate("create TABLE FriendsList(" +
                    "UserName VARCHAR(100) NOT NULL," +
                    "FriendUserName VARCHAR(100) NOT NULL)");

            stmt.executeUpdate("create TABLE Paintings(" +
                    "PaintingID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "GameName VARCHAR(100) NOT NULL," +
                    "OwnerUserName VARCHAR(100) NOT NULL, " +
                    "ImagePath VARCHAR(100), " +
                    "CurrentPlayerUserName VARCHAR(100), " +
                    "CurrentPlayerSpot INT, "+
                    "Rounds INT)");

            stmt.executeUpdate("create TABLE UserPaintings(" +
                    "PaintingID INT NOT NULL, " +
                    "userName VARCHAR(100) NOT NULL)");
        }

        catch (Exception ex) {
            System.out.println(ex);
            con.close();
            return -1;
        }
        finally {
            if (stmt != null) {stmt.close();}
        }
        con.close();
        return 1;
    }
}
