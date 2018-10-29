package com.hoymihoy.DoodleServer.Database;


import com.hoymihoy.DoodleServer.Secret;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class DBConnector {
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    
    private Connection initializeConnection() throws SQLException {
        try {
            Secret s = new Secret();
            String jdbcUrl = s.getJdbcUrl();
            String username = s.getDBUsername();
            String password = s.getDBPassword();
            
            
            con = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException ex) {
            System.out.println(ex);
            con.close();
            return con;
        }
        
        finally {
            if (stmt != null){
                stmt.close();
            }
        }
        
        return con;
    }
    
    public int dropAllTables() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            con = initializeConnection();
            
            stmt = con.createStatement();
            
            stmt.executeUpdate("DROP TABLE Users)");
        } 
        catch (SQLException ex) {
            System.out.println(ex);
            con.close();
            return -1;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if (stmt != null) {stmt.close();}
        }
        con.close();
        return 1;
    }
    
    public int createTables() throws SQLException {
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            con = initializeConnection();
            
            stmt = con.createStatement();
            
            stmt.executeUpdate("create TABLE Users(userID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, userName VARCHAR(100) NOT NULL UNIQUE, password VARCHAR(100) NOT NULL,"
                    + "firstName VARCHAR(100), last_name VARCHAR(100), birthDate DATE)");

        } 
        catch (SQLException ex) {
            System.out.println(ex);
            con.close();
            return -1;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if (stmt != null) {stmt.close();}
        }
        con.close();
        return 1;
    }
}
