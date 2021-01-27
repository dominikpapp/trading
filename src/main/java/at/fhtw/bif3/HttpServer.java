package at.fhtw.bif3;

import at.fhtw.bif3.controller.RequestController;
import at.fhtw.bif3.db.controller.ConnectionController;
import at.fhtw.bif3.exception.DBException;
import at.fhtw.bif3.util.ApplicationPropertiesReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.logging.Logger;


public class HttpServer {

    private static final Logger LOGGER = Logger.getLogger(HttpServer.class.getName());

    public static void main(String[] args) {
        try {
            ConnectionController.getConnection();
            LOGGER.info("Connection to database successful...startup continues");
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

        try {
            ServerSocket server = new ServerSocket(Integer.parseInt(ApplicationPropertiesReader.getINSTANCE().getProperty("mtcg.application.port")));
            while (true) {
                new Thread(new RequestController(server.accept())).start();
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

}