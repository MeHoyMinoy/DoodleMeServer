package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class DB_FriendsList {

    DBConnector DBC = new DBConnector();

    public int addFriendship(User user1, User user2) throws SQLException
    {
        String updateString = "INSERT INTO FriendsList(FriendID_1, FriendID_2)" +
                "VALUES(" + user1.getUserName() + ", " + user2.getUserName() +")";

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

//    public ArrayList<User> queryFriendsList(User u) throws SQLException
////    {
////
////    }

}
