package at.fhtw.bif3.db.controller;

import at.fhtw.bif3.util.DbPropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionController {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DbPropertiesReader.getINSTANCE().getProperty("mtcg.db_url"), DbPropertiesReader.getINSTANCE().getProperty("mtcg.db_user"), DbPropertiesReader.getINSTANCE().getProperty("mtcg.db_password"));
    }

}
