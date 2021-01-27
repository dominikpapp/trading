package at.fhtw.bif3.controller;

import at.fhtw.bif3.http.request.HttpMethod;
import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.response.ResponseHandler;
import at.fhtw.bif3.service.UserService;
import at.fhtw.bif3.util.UserUtil;

import static at.fhtw.bif3.util.UserUtil.extractPassword;

public class SessionsController implements Controller {

    public static final String SESSIONS_ENDPOINT = "/sessions";

    private final UserService userService = new UserService();

    @Override
    public ResponseHandler handleRequest(Request request) {
        if (request.getMethod().equals(HttpMethod.POST.name())) {
            return handlePost(request);
        }
        return notFound();
    }

    private ResponseHandler handlePost(Request request) {
        if (request.getUrl().getPath().equals(SESSIONS_ENDPOINT)) {
            return handleSessionsPost(request);
        }
        return notFound();
    }

    private ResponseHandler handleSessionsPost(Request request) {
        String username = UserUtil.extractUsername(request.getContentString());
        if (UserManager.isUserLoggedIn(username)) {
            return badRequest("User " + username + " is already logged in.");
        }
        if (!credentialsAreCorrect(username, extractPassword(request.getContentString()))) {
            return unauthorized("Invalid credentials");
        }
        UserManager.loginUser(username);
        return ok();
    }

    private boolean credentialsAreCorrect(String username, String password) {
        return userService.findByUsername(username).getPassword().equals(password);
    }

}