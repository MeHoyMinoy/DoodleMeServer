package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.SecureUserLogin;
import com.hoymihoy.DoodleServer.DTOS.User;

import java.sql.SQLException;

public class DB_User {

    DBConnector DBC = new DBConnector();

    public int createNewUser(User user) throws SQLException {
        String updateString = "INSERT INTO Users(userName,password,first_name,last_name,email,birthDate)" +
                " VALUES('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getFirstname() + "', '"
                + user.getLastname() + "', '" + user.getEmail() + "', '" + user.getBirthdate() + "');";

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

    public User queryUserID(int userID) throws SQLException {
        String queryString = "SELECT * FROM Users WHERE userID = '" + userID + "';";
        User user = new User();

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);

            while (DBC.rs.next())
            {
                user.setUserID(DBC.rs.getInt("userID"));
                user.setUsername(DBC.rs.getString("userName"));
                user.setPassword(DBC.rs.getString("password"));
                user.setFirstname(DBC.rs.getString("first_name"));
                user.setLastname(DBC.rs.getString("last_name"));
                user.setEmail(DBC.rs.getString("email"));
            }

            DBC.con.close();
            return user;

        }
        catch (Exception e) {
            System.out.println(e);
            user.setUsername("NULL");
            DBC.con.close();
            return user;
        }
        finally {
            if (DBC.stmt != null)
            {DBC.stmt.close();}
        }
    }

    public int queryLoginCredentials(SecureUserLogin sul) throws SQLException {
        String queryString = "SELECT userID FROM Users WHERE userName = '" + sul.getUserName() + "' AND password = '" + sul.getPassword() + "'";

        int userID = 0;

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);
            while(DBC.rs.next())
            {
                userID = DBC.rs.getInt("userID");
                DBC.con.close();
                return userID;
            }
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

        return userID;
    }

    public int updateUser(User user) throws SQLException {
        String updateString = "UPDATE Users SET userName = '" + user.getUsername() +
                "', first_name = '" + user.getFirstname() +
                "', last_name = '" + user.getLastname() +
                "', email = '" + user.getEmail() +
                "', birthDate = '" + user.getBirthdate() +
                "' WHERE userID = " + user.getUserID();
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
}
