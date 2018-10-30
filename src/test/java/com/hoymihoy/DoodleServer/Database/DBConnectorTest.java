package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.SecureUserLogin;
import com.hoymihoy.DoodleServer.DTOS.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DBConnectorTest {

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
    public void t02CreateNewUserTestPass() throws Exception {
        DBConnector DBC = new DBConnector();
        User testUser = new User();

        testUser.setFirstname("Devon");
        testUser.setLastname("Graves");
        testUser.setUsername("dhg5054");
        testUser.setEmail("dhg5054@psu.edu");
        testUser.setPassword("password");
        testUser.setBirthdate(java.sql.Date.valueOf("1995-09-16"));

        Assert.assertEquals(1, DBC.createNewUser(testUser));
    }

    @Test
    public void t03CreateDuplicateUserTestFail() throws Exception {
        DBConnector DBC = new DBConnector();
        User testUser = new User();

        testUser.setFirstname("Devon");
        testUser.setLastname("Graves");
        testUser.setUsername("dhg5054");
        testUser.setEmail("dhg5054@psu.edu");
        testUser.setPassword("password");
        testUser.setBirthdate(java.sql.Date.valueOf("1995-09-16"));

        Assert.assertEquals(-1, DBC.createNewUser(testUser));
    }

    @Test
    public void t06QueryUsernameTestPass() throws Exception {
        DBConnector DBC = new DBConnector();
        User user = new User();

        user = DBC.queryUserID(1);

        String userName = user.getUsername();

        Assert.assertEquals("dhg5054", userName);
    }

    @Test
    public void t07QueryUsernameTestFail() throws Exception {
        DBConnector DBC = new DBConnector();
        User user = new User();

        user = DBC.queryUserID(5);

        String userName = user.getUsername();

        Assert.assertEquals(null, userName);
    }

    @Test public void t23LoginTestPass() throws Exception {
        DBConnector DBC = new DBConnector();

        SecureUserLogin sul = new SecureUserLogin();

        sul.setUserName("dhg5054");
        sul.setPassword("password");

        Assert.assertEquals(1, DBC.queryLoginCredentials(sul));
    }

    @Test public void t24LoginTestFail() throws Exception {
        DBConnector DBC = new DBConnector();

        SecureUserLogin sup = new SecureUserLogin();

        sup.setUserName("dhg5054");
        sup.setPassword("notThePassword");

        Assert.assertEquals(0, DBC.queryLoginCredentials(sup));
    }

    @Test public void t25UpdateUserTestPass() throws Exception {
        DBConnector DBC = new DBConnector();

        User user = new User();


        user.setUsername("newUserName");
        user.setFirstname("New");
        user.setLastname("Name");
        user.setEmail("newName@osu.edu");
        user.setBirthdate(java.sql.Date.valueOf("1999-01-31"));

        Assert.assertEquals(1, DBC.updateUser(1, user));
    }

    @Test public void t26UpdateUserTestFail() throws Exception {
        DBConnector DBC = new DBConnector();

        User user = new User();


        user.setUsername("newUserName");
        user.setFirstname("New");
        user.setLastname("Name");
        user.setEmail("newName@osu.edu");
        user.setBirthdate(java.sql.Date.valueOf("1999-01-31"));

        Assert.assertEquals(0, DBC.updateUser(5, user));
    }
}
