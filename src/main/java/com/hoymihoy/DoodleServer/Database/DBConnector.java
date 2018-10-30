package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.SecureUserLogin;
import com.hoymihoy.DoodleServer.DTOS.UserModel;

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
                    + "first_name VARCHAR(100), last_name VARCHAR(100), email VARCHAR(100), birthDate DATE)");

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

    public int createNewUser(UserModel user) throws SQLException {
        String updateString = "INSERT INTO Users(userName,password,first_name,last_name,email,birthDate) VALUES('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getFirstname() + "', '"
                + user.getLastname() + "', '" + user.getEmail() + "', '" + user.getBirthdate() + "');";

        try {
            con = initializeConnection();
            stmt = con.createStatement();
            int returnValue = stmt.executeUpdate(updateString);
            con.close();
            return returnValue;
        }
        catch (Exception e) {
            System.out.println(e);
            con.close();
            return -1;
        }
        finally {
            if (stmt != null)
            {stmt.close();}
        }
    }

    public UserModel queryUserID(int userID) throws SQLException {
        String queryString = "SELECT * FROM Users WHERE userID = '" + userID + "';";
        UserModel user = new UserModel();

        try {
            con = initializeConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(queryString);

            while (rs.next())
            {
                user.setUserID(rs.getInt("userID"));
                user.setUsername(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setFirstname(rs.getString("first_name"));
                user.setLastname(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
            }

            con.close();
            return user;

        }
        catch (Exception e) {
            System.out.println(e);
            user.setUsername("NULL");
            con.close();
            return user;
        }
        finally {
            if (stmt != null)
            {stmt.close();}
        }
    }

    public int queryLoginCredentials(SecureUserLogin sul) throws SQLException {
        String queryString = "SELECT userID FROM Users WHERE userName = '" + sul.getUserName() + "' AND password = '" + sul.getPassword() + "'";

        int userID = 0;

        try {
            con = initializeConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(queryString);
            while(rs.next())
            {
                userID = rs.getInt("userID");
                con.close();
                return userID;
            }
        }
        catch (Exception e) {
            System.out.println(e);
            con.close();
            return -1;
        }
        finally {
            if (stmt != null)
            {stmt.close();}
        }

        return userID;
    }

    public int updateUser(int userID, UserModel user) throws SQLException {
        String updateString = "UPDATE Users SET userName = '" + user.getUsername() + "', first_name = '" + user.getFirstname()
                + "', last_name = '" + user.getLastname() + "', email = '" + user.getEmail() + "', birthDate = '" + user.getBirthdate()
                + "' WHERE userID = " + userID;

        try {
            con = initializeConnection();
            stmt = con.createStatement();
            int returnValue = stmt.executeUpdate(updateString);
            con.close();

            return returnValue;
        }
        catch (Exception e) {
            System.out.println(e);
            con.close();
            return -1;
        }
        finally {
            if (stmt != null)
            {stmt.close();}
        }
    }
}
