package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.SecureUserLogin;
import com.hoymihoy.DoodleServer.DTOS.User;

import java.sql.*;

public class DBConnector {
    public Connection con;
    public Statement stmt;
    public ResultSet rs;
    private String DBUsername = "admin";
    private String DBPassword = "DoodleMe";
    private String jdbcUrl = String.format(
                    "jdbc:mysql://35.236.253.241:3306/DoodleMe_DB",
                    "DoodleMe_DB",
                    "direct-return-186020:us-east4:doodle-database"
    );
    
    protected Connection initializeConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(jdbcUrl, DBUsername, DBPassword);
            stmt=con.createStatement();
        } catch (Exception ex) {
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
            
            stmt.executeUpdate("DROP TABLE Users");

            stmt.executeUpdate("DROP TABLE FriendsList");

            stmt.executeUpdate("DROP TABLE Paintings");

            stmt.executeUpdate("DROP TABLE UserPaintings");
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
                    "email VARCHAR(100), " +
                    "birthDate DATE)");

            stmt.executeUpdate("create TABLE FriendsList(" +
                    "UserName VARCHAR(100) NOT NULL," +
                    "FriendUserName VARCHAR(100) NOT NULL)");

            stmt.executeUpdate("create TABLE Paintings(" +
                    "PaintingID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "GameName VARCHAR(100) NOT NULL," +
                    "OwnerUserName VARCHAR(100) NOT NULL, " +
                    "Image VARCHAR(500) NOT NULL)");

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
