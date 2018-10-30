package com.hoymihoy.DoodleServer;

import com.hoymihoy.DoodleServer.Database.DBConnector;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DoodleMeServer {
    
    public static void main(String[] args) throws SQLException {
        DBConnector DBC = new DBConnector();
        DBC.dropAllTables();
        DBC.createTables();
        SpringApplication.run(DoodleMeServer.class, args);
    }
}
