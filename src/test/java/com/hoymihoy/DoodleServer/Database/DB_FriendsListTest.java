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
    }

    @Test
    public void t02GetFriendsTestPass() throws Exception
    {
        populateServer();
        ArrayList<String> friends = DBFL.queryFriendsList("adp5384");

        Assert.assertEquals(12, friends.size());
    }

}
