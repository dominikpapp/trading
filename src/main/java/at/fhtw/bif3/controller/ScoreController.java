package at.fhtw.bif3.controller;

import at.fhtw.bif3.controller.dto.ScoreDTO;
import at.fhtw.bif3.http.request.HttpMethod;
import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.response.ContentType;
import at.fhtw.bif3.http.response.ResponseHandler;
import at.fhtw.bif3.service.ScoreboardService;
import at.fhtw.bif3.util.UserUtil;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static at.fhtw.bif3.http.Header.AUTHORIZATION;

public class ScoreController implements Controller {
    public static final String SCORE_ENDPOINT = "/score";

    private final ScoreboardService scoreboardService = new ScoreboardService();

    @Override
    public ResponseHandler handleRequest(Request request) {
        if (request.getMethod().equals(HttpMethod.GET.name()) && request.getUrl().getPath().equals(SCORE_ENDPOINT)) {
            return handleGet(request);
        }
        return notFound();
    }

    private ResponseHandler handleGet(Request request) {
        String token = UserUtil.extractToken(request.getHeaders().get(AUTHORIZATION.getName()));
        if (UserManager.tokenNotPresent(token)) {
            return forbidden();
        }

        AtomicInteger index = new AtomicInteger(1);
        List<ScoreDTO> scores = scoreboardService.getUsersSortedByElo()
                .stream()
                .map(user -> new ScoreDTO(index.getAndIncrement(), user.getUsername(), user.getElo()))
                .collect(Collectors.toList());

        scoreboardService.getUsersSortedByElo()
                .forEach(System.out::println);

        ResponseHandler rh = ok();
        rh.setContentType(ContentType.APPLICATION_JSON.getName());
        rh.setContent(new Gson().toJson(scores));
        return rh;

    }
}