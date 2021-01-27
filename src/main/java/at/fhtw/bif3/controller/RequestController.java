package at.fhtw.bif3.controller;

import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.request.RequestHandler;
import at.fhtw.bif3.http.response.ResponseHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import static at.fhtw.bif3.http.HttpStatusCode.INTERNAL_SERVER_ERROR;
import static at.fhtw.bif3.http.HttpStatusCode.NOT_FOUND;

public class RequestController implements Runnable {

    private final Logger LOGGER = Logger.getLogger(RequestController.class.getName());
    private final Socket connectionSocket;

    public RequestController(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        try {
            RequestHandler request = RequestHandler.valueOf(connectionSocket.getInputStream());

            LOGGER.info("\nRECEIVED REQUEST: " + request.getReceivedRequest() + "\n");
            ResponseHandler response = getResponse(request);
            LOGGER.info("\nRESPONSE: " + response + "\n");
            response.send(connectionSocket.getOutputStream());
            connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ResponseHandler getResponse(Request request) {
        ResponseHandler response = new ResponseHandler();
        try {
            response = handle(request);
        } catch (IllegalArgumentException e) {
            response.setStatusCode(NOT_FOUND.getCode());
        }
        return response;
    }

    private ResponseHandler handle(Request request) {
        String path;
        try {
            path = request.getUrl().getSegments()[0];
        } catch (NullPointerException e) {
            return new ResponseHandler();
        }

        Controller controller = switch (path) {
            case "users" -> new UsersController();
            case "sessions" -> new SessionsController();
            case "packages" -> new BundleController();
            case "transactions" -> new TransactionController();
            case "cards" -> new CardController();
            case "deck" -> new DeckController();
            case "stats" -> new StatsController();
            case "score" -> new ScoreController();
            case "battles" -> new BattlesController();
            case "tradings" -> new TradingsController();
            default -> throw new IllegalArgumentException("The passed url is not valid: " + path);
        };
        try {
            return controller.handleRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setStatusCode(INTERNAL_SERVER_ERROR.getCode());
            return responseHandler;
        }
    }
}