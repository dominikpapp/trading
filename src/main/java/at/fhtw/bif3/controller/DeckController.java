package at.fhtw.bif3.controller;

import at.fhtw.bif3.domain.User;
import at.fhtw.bif3.domain.card.Card;
import at.fhtw.bif3.http.Header;
import at.fhtw.bif3.http.request.HttpMethod;
import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.response.ContentType;
import at.fhtw.bif3.http.response.ResponseHandler;
import at.fhtw.bif3.service.UserService;
import at.fhtw.bif3.util.UserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.stream.Collectors;


public class DeckController implements Controller {

    public static final String DECK_ENDPOINT = "/deck";
    public static final String FORMAT_PLAIN = "plain";
    private final UserService userService = new UserService();

    @Override
    public ResponseHandler handleRequest(Request request) {
        if (request.getMethod().equals(HttpMethod.GET.name())) {
            return handleGet(request);
        } else if (request.getMethod().equals(HttpMethod.PUT.name())) {
            return handlePut(request);
        }
        return notFound();
    }

    private ResponseHandler handlePut(Request request) {
        if (!request.getUrl().getPath().equals(DECK_ENDPOINT)) {
            return notFound();
        }

        String token = UserUtil.extractToken(request.getHeaders().get(Header.AUTHORIZATION.getName()));
        if (UserManager.tokenNotPresent(token)) {
            return forbidden();
        }

        User user = userService.findByUsername(UserUtil.extractUsernameFromToken(token));
        List<String> cardIds = new Gson().fromJson(request.getContentString(), new TypeToken<List<String>>() {
        }.getType());

        List<String> userCardIds = user.getCards().stream().map(Card::getId).collect(Collectors.toList());
        if (!userCardIds.containsAll(cardIds)) {
            return badRequest("User does not own all of the cards");
        }

        user.configureDeck(cardIds);
        userService.update(user);
        return noContent();
    }

    private ResponseHandler handleGet(Request request) {
        if (!request.getUrl().getPath().equals(DECK_ENDPOINT)) {
            return notFound();
        }

        String token = UserUtil.extractToken(request.getHeaders().get(Header.AUTHORIZATION.getName()));
        if (UserManager.tokenNotPresent(token)) {
            return forbidden();
        }

        String format = request.getUrl().getParameter().get("format");
        if (FORMAT_PLAIN.equals(format)) {
            return handleGetPlain(request);
        } else {
            return handleGetJson(request);
        }
    }

    private ResponseHandler handleGetPlain(Request request) {
        String token = UserUtil.extractToken(request.getHeaders().get(Header.AUTHORIZATION.getName()));
        String username = UserManager.getUsernameForToken(token);
        ResponseHandler rh = ok();
        rh.setContent(userService.findByUsername(username).getDeck().toString());
        return rh;
    }

    private ResponseHandler handleGetJson(Request request) {
        String token = UserUtil.extractToken(request.getHeaders().get(Header.AUTHORIZATION.getName()));
        String username = UserManager.getUsernameForToken(token);
        User user = userService.findByUsername(username);
        ResponseHandler rh = ok();
        rh.setContentType(ContentType.APPLICATION_JSON.getName());
        rh.setContent(new Gson().toJson(user.getDeck()));
        return rh;
    }
}
