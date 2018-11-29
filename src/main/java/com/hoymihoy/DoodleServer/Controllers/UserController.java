package com.hoymihoy.DoodleServer.Controllers;

import com.hoymihoy.DoodleServer.DTOS.SecureUserLogin;
import com.hoymihoy.DoodleServer.DTOS.User;
import com.hoymihoy.DoodleServer.Database.DB_User;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin
@RestController
public class UserController {

    DB_User DBU = new DB_User();

    public UserController() throws SQLException {

        User AdminTest = new User();
        AdminTest.setUserName("Admin");
        AdminTest.setPassword("AdminPass");
        User UserTest = new User();
        UserTest.setUserName("Bob");
        UserTest.setPassword("Jerry");
        UserTest.setFirstName("Bob");
        UserTest.setLastName("Jones");
        UserTest.setEmail("bobjones123@gmail.com");

        DBU.createNewUser(AdminTest);
        DBU.createNewUser(UserTest);
    }

    @CrossOrigin
    @PostMapping (path =  "/Login")
    public int ReceiveLoginInformation(@RequestBody SecureUserLogin user)
    {
        // If userID = -1, there was an error during the query
        // If userID = 0, no record was found with the userName and password
        // If userID = anything else, a userID with that userName and password was found
        int userID = -1;
        try {
            userID = DBU.queryLoginCredentials(user);
            return userID;
        } catch (SQLException e) {
            e.printStackTrace();
            return userID;
        }
    }

    @CrossOrigin
    @PostMapping (path = "/CreateProfile")
    public int CreateNewProfile(@RequestBody User newUser)
    {
        // If status = 0, there was an error while creating the query
        // If status = -1, there is already a user with that userName
        // If status = 1, a new profile was created successfully
        int status = 0;
        try {
            status = DBU.createNewUser(newUser);
            return status;
        } catch (SQLException e) {
            e.printStackTrace();
            return status;
        }
    }

    @CrossOrigin
    @PostMapping (path =  "/UpdateProfile")
    public int UpdateProfileInformation(@RequestBody User user)
    {
        // Return with the number of entries updated. This number should be 1 if successful
        int entriesUpdated = -1;
        try {
            entriesUpdated = DBU.updateUser(user);
            if (entriesUpdated == 1)
            {
                return entriesUpdated;
            }
            else
            {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @CrossOrigin
    @PostMapping (path = "/GetUserData")
    public User GetUserData(@RequestParam(value = "user") int user) throws SQLException {
        User userObject = DBU.queryUserID(user);

        return userObject;
    }
}

/*
{
    "username": "dhg5054",
    "password": "yeet",
    "firstname": "Devon",
    "lastname": "Graves",
    "birthdate": "1995-09-16",
    "email": "dhg5054@psu.edu"
}
 */
