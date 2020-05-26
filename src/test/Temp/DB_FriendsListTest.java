package com.hoymihoy.DoodleServer.Database;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

import static com.hoymihoy.DoodleServer.DoodleMeServer.populateServer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DB_FriendsListTest {

    DB_FriendsList DBFL = new DB_FriendsList();

    @Test
    public void t00dropAllTablesTest() throws Exception {
        DBConnector DBC = new DBConnector();
        Assert.assertEquals(1, DBC.dropAllTables());
    }

    @Test
    public void t01CreateTablesTestPass() throws Exception {
        DBConnector DBC = new DBConnector();
        Assert.assertEquals(1, DBC.createTables());
        populateServer();
    }

    @Test
    public void t02AddFriendTestFail() throws Exception {
        int result = DBFL.addFriendship("adp5384", "George Cermufflin");

        Assert.assertEquals(2, result); // Person doesn't exist

        result = DBFL.addFriendship("adp5384", "Neil DeGrasse Tyson");

       Assert.assertEquals(2, result); // Person doesn't exist
    }

    @Test
    public void t03AddFriendTestPass() throws Exception {
        int result = DBFL.addFriendship("adp5384", "admin2");

        Assert.assertEquals(1, result);
    }


    @Test
    public void t04getFriendsTestPass() throws Exception
    {
        ArrayList<String> friends = DBFL.queryFriendsList("adp5384");

        Assert.assertEquals(2, friends.size());
    }

    @Test
    public void t05queryAlreadyFriendsTest_AlreadyFriends() throws Exception
    {
        String user1 = "adp5384";
        String user2 = "admin1";

        // If the friend already exists, a 3 will be returned.
        int result = DBFL.addFriendship(user1, user2);

        Assert.assertEquals(3, result);
    }

    @Test
    public void t06queryAlreadyFriendsTest_NotAlreadyFriends() throws Exception
    {
        String user1 = "adp5384";
        String user2 = "admin3";

        // The user exists but they are not friends. Should return a 1 for creating a new friendship
        int result = DBFL.addFriendship(user1, user2);

        Assert.assertEquals(1, result);
    }

    @Test
    public void t07queryUserExistsTest_UserDoesNotExist() throws Exception
    {
        String user1 = "adp5384";
        String user2 = "AdMiN1";

        // The user does not exist. This should return a 2
        int result = DBFL.addFriendship(user1, user2);

        Assert.assertEquals(2, result);
    }

}
