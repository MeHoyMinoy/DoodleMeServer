package com.hoymihoy.DoodleServer.Database;


import com.hoymihoy.DoodleServer.Secret;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class DBConnector {
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    
    private Connection initializeConnection() throws SQLException {
        try {
            Secret s = new Secret();
            String jdbcUrl = s.getJdbcUrl();
            String username = s.getDBUsername();
            String password = s.getDBPassword();
            
            con = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException ex) {
            System.out.println(ex);
            con.close();
            return con;
        }
        
        finally {
            if (stmt != null){
                stmt.close();
            }
        }
        
        return con;
    }
    
}
