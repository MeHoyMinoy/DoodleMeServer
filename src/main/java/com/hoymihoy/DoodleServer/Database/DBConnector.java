package com.hoymihoy.DoodleServer.Database;

import javax.validation.constraints.Null;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class DBConnector {
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private String DBUsername = "admin";
    private String DBPassword = "DoodleMe";
    private String jdbcUrl = String.format(
                    "jdbc:mysql://35.236.253.241:3306/DoodleMe_DB",
                    "DoodleMe_DB",
                    "direct-return-186020:us-east4:doodle-database"
    );
    
    private Connection initializeConnection() throws SQLException {
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
                    + "firstName VARCHAR(100), last_name VARCHAR(100), birthDate DATE)");

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
