package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.SecureUserLogin;
import com.hoymihoy.DoodleServer.DTOS.User;

import java.sql.SQLException;

public class DB_User {

    DBConnector DBC = new DBConnector();

    public int createNewUser(User user) throws SQLException {
        String updateString = "INSERT INTO Users(userName, password, firstName, lastName, nickName, birthDate)" +
                " VALUES('" + user.getUserName() + "', '" + user.getPassword() + "', '" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getNickName() + "', '" + user.getBirthDate() + "');";

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
        String queryString = "SELECT * FROM Users " +
                "WHERE userName COLLATE utf8_bin = '" + userName + "' ";
        User user = new User();

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);

            while (DBC.rs.next())
            {
                user.setUserName(DBC.rs.getString("userName"));
                user.setFirstName(DBC.rs.getString("firstName"));
                user.setLastName(DBC.rs.getString("lastName"));
                user.setNickName(DBC.rs.getString("nickName"));
                user.setBirthDate(DBC.rs.getDate("birthDate"));
            }

            DBC.con.close();
            return user;

        }
        catch (Exception e) {
            System.out.println(e);
            user.setUserName(null);
            DBC.con.close();
            return user;
        }
        finally {
            if (DBC.stmt != null)
            {DBC.stmt.close();}
        }
    }

    public int queryLoginCredentials(SecureUserLogin sul) throws SQLException {
        String queryString = "SELECT count(*) AS rows FROM Users WHERE userName COLLATE utf8_bin = '" + sul.getUserName() + "' AND password COLLATE utf8_bin = '" + sul.getPassword() + "'";

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

        System.out.println(user.getUserName());
        String updateString = "UPDATE Users SET " + " nickName = '" + user.getNickName() + "' WHERE userName = '" + user.getUserName() + "'";
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
