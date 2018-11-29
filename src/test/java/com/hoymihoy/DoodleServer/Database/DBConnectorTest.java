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
        DB_User DBU = new DB_User();
        User testUser = new User();

        testUser.setFirstName("Devon");
        testUser.setLastName("Graves");
        testUser.setUsername("dhg5054");
        testUser.setEmail("dhg5054@psu.edu");
        testUser.setPassword("password");
        testUser.setBirthDate(java.sql.Date.valueOf("1995-09-16"));

        Assert.assertEquals(1, DBU.createNewUser(testUser));
    }

    @Test
    public void t03CreateDuplicateUserTestFail() throws Exception {
        DB_User DBU = new DB_User();
        User testUser = new User();

        testUser.setFirstName("Devon");
        testUser.setLastName("Graves");
        testUser.setUsername("dhg5054");
        testUser.setEmail("dhg5054@psu.edu");
        testUser.setPassword("password");
        testUser.setBirthDate(java.sql.Date.valueOf("1995-09-16"));

        Assert.assertEquals(-1, DBU.createNewUser(testUser));
    }

    @Test
    public void t06QueryUsernameTestPass() throws Exception {
        DB_User DBU = new DB_User();
        User user;

        user = DBU.queryUserID(1);

        String userName = user.getUsername();

        Assert.assertEquals("dhg5054", userName);
    }

    @Test
    public void t07QueryUsernameTestFail() throws Exception {
        DB_User DBU = new DB_User();
        User user;

        user = DBU.queryUserID(5);

        String userName = user.getUsername();

        Assert.assertEquals(null, userName);
    }

    @Test public void t23LoginTestPass() throws Exception {
        DB_User DBU = new DB_User();

        SecureUserLogin sul = new SecureUserLogin();

        sul.setUserName("dhg5054");
        sul.setPassword("password");

        Assert.assertEquals(1, DBU.queryLoginCredentials(sul));
    }

    @Test public void t24LoginTestFail() throws Exception {
        DB_User DBU = new DB_User();

        SecureUserLogin sup = new SecureUserLogin();

        sup.setUserName("dhg5054");
        sup.setPassword("notThePassword");

        Assert.assertEquals(0, DBU.queryLoginCredentials(sup));
    }

    @Test public void t25UpdateUserTestPass() throws Exception {
        DB_User DBU = new DB_User();

        User user = new User();

        user.setUserID(1);
        user.setUsername("newUserName");
        user.setFirstName("New");
        user.setLastName("Name");
        user.setEmail("newName@osu.edu");
        user.setBirthDate(java.sql.Date.valueOf("1999-01-31"));

        Assert.assertEquals(1, DBU.updateUser(user));
    }

    @Test public void t26UpdateUserTestFail() throws Exception {
        DB_User DBU = new DB_User();

        User user = new User();

        user.setUserID(2);
        user.setUsername("newUserName");
        user.setFirstName("New");
        user.setLastName("Name");
        user.setEmail("newName@osu.edu");
        user.setBirthDate(java.sql.Date.valueOf("1999-01-31"));

        Assert.assertEquals(0, DBU.updateUser(user));
    }
}
