package com.hoymihoy.DoodleServer.Controllers;

import com.hoymihoy.DoodleServer.DTOS.Painting;
import com.hoymihoy.DoodleServer.DTOS.User;
import com.hoymihoy.DoodleServer.Database.DB_FriendsList;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@CrossOrigin
@RestController
public class FriendsListController {

    DB_FriendsList DBFL = new DB_FriendsList();

    public FriendsListController() {}

    @CrossOrigin
    @PostMapping(path = "/addFriend")
    public int CreateNewFriendship(@RequestBody User user1, User user2)
    {
        // If status = 0, there was an error while creating the query
        // If status = -1, there is already a friendship for those users
        // If status = 1, a new friendship was created
        int status = 0;
        try {
            status = DBFL.addFriendship(user1, user2);
            return status;
        } catch (SQLException e) {
            e.printStackTrace();
            return status;
        }
    }


    @CrossOrigin
    @PostMapping(path = "/getFriendsList")
    public ArrayList<String> GetFriendsList(@RequestParam (value = "userName") String userName)
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
