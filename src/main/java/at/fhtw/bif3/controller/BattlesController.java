package at.fhtw.bif3.controller;

import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.response.ResponseHandler;
import at.fhtw.bif3.service.UserService;

public class BattlesController implements Controller {
    public static final String BATTLES_ENDPOINT = "/battles";
    private final UserService userService = new UserService();

    @Override
    public ResponseHandler handleRequest(Request request) {
        return notImplemented("Battles are not implemented");
    }
}
