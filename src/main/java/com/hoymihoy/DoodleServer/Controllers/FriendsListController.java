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
        // If status = 0, there was an error while creating the query
        // If status = -1, there is already a friendship for those users
        // If status = 1, a new friendship was created
        int status = 0;
        try {
            status = DBFL.addFriendship(userName, friendUserName);
            return status;
        } catch (SQLException e) {
            e.printStackTrace();
            return status;
        }



    }


    @CrossOrigin
    @PostMapping(path = "/GetFriendsList")
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

    //Function returns 1 for if user already is in friendsList
    //Function return 0 for if user is not in friendsList
    //Function return -1 if Exception was thrown
    public int queryFriendExists(@RequestParam (value = "userName") String userName)
    {
        ArrayList<String> friends;
        try {
            friends = DBFL.queryFriendsList(userName);
            if(friends.contains(userName)){
                return 1;       //User in friendsList
            } else{
                return 0;       //No user in friendsList
            }

        } catch (SQLException e) {
            e.printStackTrace();
            friends = new ArrayList<>();
            return -1;
        }
    }


    public int queryUserExists(@RequestParam (value = "userName") String userName)
    {
        DB_User DBU = new DB_User();
        User user = new User();
        try{
            user = DBU.queryUserName(userName);
            if(user.getUserName().contains(null)){
                return 0;
            }else{
                return 1;
            }
        } catch(Exception E){
            return -1;
        }
    }

}
