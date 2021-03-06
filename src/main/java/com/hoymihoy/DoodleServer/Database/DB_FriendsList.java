package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class DB_FriendsList {

    DBConnector DBC = new DBConnector();

    // User 1 is the person making the friendship request
    // User 2 is the person being added
    public int addFriendship(String user1, String user2) throws SQLException
    {

        int userExists = queryUserExists(user2);          //check if user exists
        int friendExists = queryAlreadyFriends(user1, user2);       //check if friend exists

        if(userExists == 0) {
            return 2;       //user doesn't exists
        } else if(userExists == -1){
            return -1;      //error
        }

        if(friendExists == 1) {
            return 3;       //friend does exists
        } else if(friendExists == -1){
            return -1;      //error
        }

        String updateString = "INSERT INTO FriendsList(UserName, FriendUserName)" +
                    "VALUES('" + user1 + "', '" + user2 +"')";

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



    public int deleteFriendship(String user1, String user2) throws SQLException
    {

        int userExists = queryUserExists(user2);                    //check if user exists
        int friendExists = queryAlreadyFriends(user1, user2);       //check if friend exists

        if(userExists == 0) {
            return 2;       //user doesn't exists
        } else if(userExists == -1){
            return -1;      //error
        }

        if(friendExists == 0) {
            return 3;       //Not a Friend
        } else if(friendExists == -1){
            return -1;      //error
        }

        String updateString = "DELETE FROM FriendsList WHERE UserName='"+ user1 +"' AND FriendUserName='"+ user2 +"'";



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
                "WHERE UserName COLLATE utf8_bin = '" + userName + "'";

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

    //Function returns 1 for if user already is in friendsList
    //Function return 0 for if user is not in friendsList
    //Function return -1 if Exception was thrown
    public int queryAlreadyFriends(String User1, String User2)
    {
        ArrayList<String> friends;
        try {
            friends = queryFriendsList(User1);
            if(friends.contains(User2)){
                return 1;       //User in friendsList
            } else{
                return 0;       //No user in friendsList
            }

        } catch (SQLException e) {
            e.printStackTrace();
            //friends = new ArrayList<>();
            return -1;
        }
    }

    //Function returns 1 for if user already exists
    //Function return 0 for if user does not exists
    //Function return -1 if Exception was thrown
    public int queryUserExists(String userName)
    {
        DB_User DBU = new DB_User();
        User user;
        try{
            user = DBU.queryUserName(userName);
            if(user.getUserName() == null){
                return 0;
            }else{
                return 1;
            }
        } catch(Exception E){
            return -1;
        }
    }



}
