package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class DB_FriendsList {

    DBConnector DBC = new DBConnector();

    public int addFriendship(String user1, String user2) throws SQLException
    {
        // This function will return a value indicating if the friendship was successful or not
        // If result = 1, the friendship was successful
        // If result = 2, the FriendUserName does not exist
        // If result = 3, the friendship already exists
        // If result = -1, some other error happened

        int result = 0;

        String updateString = "INSERT INTO FriendsList(UserName, FriendUserName)" +
                "VALUES('" + user1 + "', '" + user2 +"')";

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            result = DBC.stmt.executeUpdate(updateString);
            DBC.con.close();
            return result;
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

    public ArrayList<String> queryFriendsList(String userName) throws SQLException
    {
        ArrayList<String> friends = new ArrayList<>();

        String queryString = "SELECT FriendUserName " +
                "FROM FriendsList " +
                "WHERE UserName = '" + userName + "'";

        try {
            DBC.con = DBC.initializeConnection();
            DBC.stmt = DBC.con.createStatement();
            DBC.rs = DBC.stmt.executeQuery(queryString);

            while (DBC.rs.next())
            {
                friends.add(DBC.rs.getString(1));
            }

            DBC.con.close();
            return friends;

        }
        catch (Exception e) {
            System.out.println(e);
            DBC.con.close();
            return friends;
        }
        finally {
            if (DBC.stmt != null)
            {DBC.stmt.close();}
        }
    }

}
