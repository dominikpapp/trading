package at.fhtw.bif3.controller;

import at.fhtw.bif3.domain.User;
import at.fhtw.bif3.exception.DBException;
import at.fhtw.bif3.exception.EntityNotFoundException;
import at.fhtw.bif3.http.request.HttpMethod;
import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.response.ContentType;
import at.fhtw.bif3.http.response.ResponseHandler;
import at.fhtw.bif3.service.UserService;
import at.fhtw.bif3.util.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static at.fhtw.bif3.http.Header.AUTHORIZATION;
import static at.fhtw.bif3.util.UserUtil.extractPassword;
import static at.fhtw.bif3.util.UserUtil.extractUsername;

public class UsersController implements Controller {
    public static final String USERS_ENDPOINT = "/users";
    private final UserService userService = new UserService();

    @Override
    public ResponseHandler handleRequest(Request request) {
        if (request.getMethod().equals(HttpMethod.POST.name())) {
            return handlePost(request);
        } else if (request.getMethod().equals(HttpMethod.PUT.name())) {
            return handlePut(request);
        } else if (request.getMethod().equals(HttpMethod.GET.name())) {
            return handleGet(request);
        }

        return notFound();
    }

    private ResponseHandler handleGet(Request request) {
        String token = UserUtil.extractToken(request.getHeaders().get(AUTHORIZATION.getName()));
        if (UserManager.tokenNotPresent(token)) {
            return forbidden();
        }

        String[] segments = request.getUrl().getSegments();
        if (segments.length != 2) {
            return notFound();
        }

        try {
            ResponseHandler rh = ok();
            rh.setContentType(ContentType.APPLICATION_JSON.getName());
            rh.setContent(new Gson().toJson(userService.findByUsername(segments[1])));
            return rh;


        } catch (EntityNotFoundException e) {
            return badRequest("User with username " + segments[1] + " could not be found!");
        }
    }

    private ResponseHandler handlePost(Request request) {
        if (request.getUrl().getPath().equals(USERS_ENDPOINT)) {
            return handleUsersPost(request);
        }
        return notFound();
    }

    private ResponseHandler handlePut(Request request) {
        String token = UserUtil.extractToken(request.getHeaders().get(AUTHORIZATION.getName()));
        if (UserManager.tokenNotPresent(token)) {
            return forbidden();
        }

        String[] segments = request.getUrl().getSegments();
        String username = UserManager.getUsernameForToken(token);
        if (segments.length != 2) {
            return notFound();

        }
        if (!segments[1].equals(username)) {
            return forbidden();
        }
        User user = new GsonBuilder().create().fromJson(request.getContentString(), User.class);
        applyUpdatedAttributes(user, username);
        ResponseHandler rh = ok();
        rh.setContent("Request successful");
        rh.setContentType(ContentType.TEXT_HTML.getName());
        return rh;
    }

    private void applyUpdatedAttributes(User user, String username) {
        User existingUser = userService.findByUsername(username);

        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getBio() != null) {
            existingUser.setBio(user.getBio());
        }
        if (user.getImage() != null) {
            existingUser.setImage(user.getImage());
        }

        userService.update(existingUser);
    }

    private ResponseHandler handleUsersPost(Request request) {
        String username = extractUsername(request.getContentString());
        String password = extractPassword(request.getContentString());
        try {
            userService.create(new User(username, username, password));
        } catch (DBException e) {
            return badRequest("Creating user " + username + " failed.");
        }
        ResponseHandler rh = created();
        rh.setContent("Creating user " + username + " successful");
        rh.setContentType(ContentType.TEXT_HTML.getName());
        return rh;
    }
}
