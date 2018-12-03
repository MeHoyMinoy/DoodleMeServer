package com.hoymihoy.DoodleServer.Controllers;

import com.hoymihoy.DoodleServer.DTOS.Painting;
import com.hoymihoy.DoodleServer.DTOS.User;
import com.hoymihoy.DoodleServer.Database.DB_FriendsList;
import com.hoymihoy.DoodleServer.Database.DB_User;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@CrossOrigin
@RestController
public class FriendsListController {

    DB_FriendsList DBFL = new DB_FriendsList();

    public FriendsListController() {}

    @CrossOrigin
    @PostMapping(path = "/AddFriend")
    public int CreateNewFriendship(@RequestParam (value = "userName") String userName,
                                   @RequestParam (value = "friendUserName") String friendUserName)
    {
        // If status = -1, there was an error while creating the query
        // If status = 1, a new friendship was created
        // If status = 2, the user being added does not exist
        // If status = 3, the user being added is already a friend
        int status = -1;
        try {
            status = DBFL.addFriendship(userName, friendUserName);
            return status;
        } catch (SQLException e) {
            e.printStackTrace();
            return status;
        }



    }


    @CrossOrigin
    @GetMapping(path = "/GetFriendsList")
    public ArrayList<String> GetFriendsList(@RequestParam (value = "user") String userName)
    {
        ArrayList<String> friends;
        try {
            friends = DBFL.queryFriendsList(userName);
            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
            friends = new ArrayList<>();
            return friends;
        }
    }

}
