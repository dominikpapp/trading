package at.fhtw.bif3.controller;

import at.fhtw.bif3.domain.User;
import at.fhtw.bif3.http.request.HttpMethod;
import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.response.ResponseHandler;
import at.fhtw.bif3.service.UserService;
import at.fhtw.bif3.util.UserUtil;
import com.google.gson.Gson;

import static at.fhtw.bif3.http.Header.AUTHORIZATION;
import static at.fhtw.bif3.util.UserUtil.extractUsernameFromToken;

public class CardController implements Controller {

    public static final String CARDS_ENDPOINT = "/cards";
    private final UserService userService = new UserService();

    @Override
    public ResponseHandler handleRequest(Request request) {
        if (request.getMethod().equals(HttpMethod.GET.name())) {
            return handleGet(request);
        }
        return notFound();
    }

    private ResponseHandler handleGet(Request request) {
        if (request.getUrl().getPath().equals(CARDS_ENDPOINT)) {
            return handleCardsGet(request);
        }
        return notFound();
    }

    private ResponseHandler handleCardsGet(Request request) {
        ResponseHandler responseHandler = ok();
        String authorizationHeaderValue = request.getHeaders().get(AUTHORIZATION.getName());
        if (authorizationHeaderValue == null) {
            return badRequest("Authorization token is missing");
        }

        String token = UserUtil.extractToken(authorizationHeaderValue);
        if (UserManager.tokenNotPresent(token)) {
            return forbidden();
        }

        User user = userService.findByUsername(extractUsernameFromToken(token));
        responseHandler.setContent(new Gson().toJson(user.getCards()));
        return responseHandler;
    }
}

