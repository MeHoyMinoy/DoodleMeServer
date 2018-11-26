package com.hoymihoy.DoodleServer.Controllers;

import com.hoymihoy.DoodleServer.DTOS.Painting;
import com.hoymihoy.DoodleServer.Database.DBConnector;
import com.hoymihoy.DoodleServer.Database.DB_Paintings;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

public class PaintingsController {

    DB_Paintings DBP = new DB_Paintings();

    public PaintingsController()
    {

    }

    @CrossOrigin
    @PostMapping (path = "/CreateProfile")
    public int CreateNewProfile(@RequestBody Painting newPainting) throws SQLException
    {
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
    @PostMapping (path =  "/UpdateProfile")
    public int UpdateProfileInformation(@RequestBody Painting p)
    {
        // Return with the number of entries updated. This number should be 1 if successful
        int entriesUpdated = -1;
        try {
            entriesUpdated = DBP.updatePainting(p);
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
    @GetMapping(path = "/GetUserData")
    public Painting GetUserData(@RequestParam(value = "painting") int PaintingID) throws SQLException {
        Painting p = DBP.queryPaintingID(PaintingID);

        return p;
    }
}
