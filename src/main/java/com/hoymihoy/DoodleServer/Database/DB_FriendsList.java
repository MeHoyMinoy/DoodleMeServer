package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class DB_FriendsList {

    DBConnector DBC = new DBConnector();

    public int addFriendship(String user1, String user2) throws SQLException
    {
        String updateString = "INSERT INTO FriendsList(FriendID_1, FriendID_2)" +
                "VALUES(" + user1 + ", " + user2 +")";

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
