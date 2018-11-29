package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.SecureUserLogin;
import com.hoymihoy.DoodleServer.DTOS.User;

import java.sql.SQLException;

public class DB_User {

    DBConnector DBC = new DBConnector();

    public int createNewUser(User user) throws SQLException {
        String updateString = "INSERT INTO Users(userName,password,firstName,lastName,email,birthDate)" +
                " VALUES('" + user.getUserName() + "', '" + user.getPassword() + "', '" + user.getFirstName() + "', '"
                + user.getLastName() + "', '" + user.getEmail() + "', '" + user.getBirthDate() + "');";

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
        String queryString = "SELECT * FROM Users WHERE userID = " + userID + ";";
        User user = new User();

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);

            while (DBC.rs.next())
            {
                user.setUserID(DBC.rs.getInt("userID"));
                user.setUserName(DBC.rs.getString("userName"));
                user.setPassword(DBC.rs.getString("password"));
                user.setFirstName(DBC.rs.getString("firstName"));
                user.setLastName(DBC.rs.getString("lastName"));
                user.setEmail(DBC.rs.getString("email"));
                user.setBirthDate(DBC.rs.getDate("birthDate"));
            }

            DBC.con.close();
            return user;

        }
        catch (Exception e) {
            System.out.println(e);
            user.setUserName("NULL");
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
        String updateString = "UPDATE Users SET userName = '" + user.getUserName() +
                "', firstName = '" + user.getFirstName() +
                "', lastName = '" + user.getLastName() +
                "', email = '" + user.getEmail() +
                "', birthDate = '" + user.getBirthDate() +
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
