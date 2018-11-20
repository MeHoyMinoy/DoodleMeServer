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
            
            stmt.executeUpdate("create TABLE Users(userID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, userName VARCHAR(100) NOT NULL UNIQUE, password VARCHAR(100) NOT NULL,"
                    + "first_name VARCHAR(100), last_name VARCHAR(100), email VARCHAR(100), birthDate DATE)");

            stmt.executeUpdate("create TABLE Paintings(paintingID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, creatorID)")
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
