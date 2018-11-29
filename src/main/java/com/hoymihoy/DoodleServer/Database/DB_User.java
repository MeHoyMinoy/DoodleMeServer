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

    public User queryUserName(String userName) throws SQLException {
        String queryString = "SELECT * FROM Users WHERE userName = '" + userName + "';";
        User user = new User();

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);

            while (DBC.rs.next())
            {
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
        String queryString = "SELECT count(*) AS rows FROM Users WHERE userName = '" + sul.getUserName() + "' AND password = '" + sul.getPassword() + "'";

        int rows = 0;

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);
            while(DBC.rs.next())
            {
                rows = DBC.rs.getInt("rows");
                DBC.con.close();
                return rows;
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

        return rows;
    }

    public int updateUser(User user) throws SQLException {
        String updateString = "UPDATE Users SET "+
                "firstName = '" + user.getFirstName() +
                "', lastName = '" + user.getLastName() +
                "', email = '" + user.getEmail() +
                "', birthDate = '" + user.getBirthDate() +
                "' WHERE userName = '" + user.getUserName() +"'";
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
