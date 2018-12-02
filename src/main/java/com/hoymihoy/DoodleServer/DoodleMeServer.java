package com.hoymihoy.DoodleServer;

import java.sql.Date;
import com.hoymihoy.DoodleServer.DTOS.*;
import com.hoymihoy.DoodleServer.Database.*;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoodleMeServer {
    
    public static void main(String[] args) throws SQLException {
        DBConnector DBC = new DBConnector();
        DBC.dropAllTables();
        DBC.createTables();
        populateServer();
        SpringApplication.run(DoodleMeServer.class, args);

    }

    public static void populateServer(){
        DB_User DBU =   new DB_User();
        User person = new User();
        SecureUserLogin SUL = new SecureUserLogin();
        try{
            //Populating CreateUser
            /////////////////////////////////////
            person.setBirthDate(java.sql.Date.valueOf("1997-09-16"));
            person.setUserName("admin");
            person.setPassword("admin");
            person.setFirstName("Ever");
            person.setLastName("Greatest");
            person.setEmail("admin@gmail.com");
            DBU.createNewUser(person);
            /////////////////////////////////////
        } catch(Exception E){
        }
//        //Populating FriendsList
//        Painting p = new Painting();
//        p.setImage("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");
//        DB_Paintings DBP =   new DB_Paintings();
//        try{
//            DBP.createNewPainting(p);
//        } catch(Exception E){
//        }
//        DB_FriendsList DBF =   new DB_FriendsList();
//        DBConnector DBUP =   new DBConnector();

    }

}
