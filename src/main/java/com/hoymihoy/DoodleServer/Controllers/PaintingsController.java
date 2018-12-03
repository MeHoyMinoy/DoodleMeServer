package com.hoymihoy.DoodleServer.Controllers;

import com.hoymihoy.DoodleServer.DTOS.PaintingUsers;
import com.hoymihoy.DoodleServer.DTOS.Painting;
import com.hoymihoy.DoodleServer.DTOS.User;
import com.hoymihoy.DoodleServer.Database.DB_Paintings;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@CrossOrigin
@RestController
public class PaintingsController {

    DB_Paintings DBP = new DB_Paintings();

    public PaintingsController() {

    }

    @CrossOrigin
    @PostMapping(path = "/CreatePainting")
    public int CreateNewPainting(@RequestBody Painting newPainting) throws SQLException {
        // If status = 0, there was an error while creating the query
        // If status = -1, there is already a user with that userName
        // If status = 1, a new profile was created successfully
        int status = 0;
        try {
            status = DBP.createNewPainting(newPainting);
            return status;
        } catch (SQLException e) {
            e.printStackTrace();
            return status;
        }
    }

    @CrossOrigin
    @PostMapping(path = "/UpdatePainting")
    public int UpdatePainting(@RequestBody Painting p) {
        // Return with the number of entries updated. This number should be 1 if successful
        int entriesUpdated = -1;
        try {
            entriesUpdated = DBP.updatePainting(p);
            if (entriesUpdated == 1) {
                return entriesUpdated;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @CrossOrigin
    @GetMapping(path = "/GetPainting")
    public Painting GetPainting(@RequestParam(value = "painting") int paintingID) throws SQLException {
        Painting p = DBP.queryPaintingID(paintingID);

        return p;
    }

    @CrossOrigin
    @GetMapping(path = "/GetFeed")
    public ArrayList<Painting> queryUserPaintings(@RequestParam String user) throws SQLException {
        ArrayList<Painting> userPaintings = DBP.getUserPaintings(user);
        return userPaintings;
    }

    @CrossOrigin
    @PostMapping(path = "/CreateGroup")
    public int CreateNewGroup(@RequestBody PaintingUsers newPaintingUsers) throws SQLException {
        // createNewGame() returns the number of rows added to UserPaintings
        // This is equal to the number of people added to a game
        // Will return -1 if an error has occurred
        int result = DBP.createNewGame(newPaintingUsers);
        return result;
    }
}
