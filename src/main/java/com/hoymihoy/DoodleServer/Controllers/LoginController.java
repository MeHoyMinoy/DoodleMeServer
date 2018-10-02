package com.hoymihoy.DoodleServer.Controllers;

import com.hoymihoy.DoodleServer.DTOS.SecureUserLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginController {
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public int ReceiveLoginInformation(@RequestBody SecureUserLogin user)
    {
        int userID = -1;
        
        // Query database for matching userName and password
        // If query returns a UserID, send it back to the application
        return -1;
    }
}
