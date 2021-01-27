package at.fhtw.bif3.controller;

import at.fhtw.bif3.controller.dto.StatsDTO;
import at.fhtw.bif3.domain.User;
import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.response.ContentType;
import at.fhtw.bif3.http.response.ResponseHandler;
import at.fhtw.bif3.service.UserService;
import at.fhtw.bif3.util.UserUtil;
import com.google.gson.Gson;

import static at.fhtw.bif3.http.request.HttpMethod.GET;
import static at.fhtw.bif3.util.UserUtil.extractUsernameFromToken;

public class StatsController implements Controller {

    public static final String STATS_ENDPOINT = "/stats";

    private final UserService userService = new UserService();

    @Override
    public ResponseHandler handleRequest(Request request) {
        if (request.getMethod().equals(GET.name()) && request.getUrl().getPath().equals(STATS_ENDPOINT)) {
            return handleGet(request);
        }
        return notFound();
    }

    private ResponseHandler handleGet(Request request) {
        String token = UserUtil.extractToken(request.getHeaders().get("Authorization"));
        if (UserManager.tokenNotPresent(token)) {
            return forbidden();
        }

        String username = extractUsernameFromToken(token);
        User user = userService.findByUsername(username);
        ResponseHandler rh = ok();
        rh.setContentType(ContentType.APPLICATION_JSON.getName());
        rh.setContent(new Gson().toJson(new StatsDTO(user.getGamesPlayed(), user.getElo())));
        return rh;
    }
}
