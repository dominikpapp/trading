package at.fhtw.bif3.controller;

import at.fhtw.bif3.controller.dto.TradingDTO;
import at.fhtw.bif3.domain.TradingDeal;
import at.fhtw.bif3.domain.User;
import at.fhtw.bif3.domain.card.Card;
import at.fhtw.bif3.exception.EntityNotFoundException;
import at.fhtw.bif3.http.request.HttpMethod;
import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.response.ResponseHandler;
import at.fhtw.bif3.service.CardService;
import at.fhtw.bif3.service.TradingDealService;
import at.fhtw.bif3.service.UserService;
import at.fhtw.bif3.util.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import static at.fhtw.bif3.http.Header.AUTHORIZATION;
import static at.fhtw.bif3.http.response.ContentType.APPLICATION_JSON;
import static java.util.stream.Collectors.toList;

public class TradingsController implements Controller {

    public static final String TRADINGS_ENDPOINT = "/tradings";
    private final TradingDealService tradingService = new TradingDealService();
    private final UserService userService = new UserService();

    @Override
    public ResponseHandler handleRequest(Request request) {
        try {
            String token = UserUtil.extractToken(request.getHeaders().get(AUTHORIZATION.getName()));
            if (UserManager.tokenNotPresent(token)) {
                return forbidden();
            }
        } catch (NullPointerException e) {
            return forbidden();
        }

        if (request.getMethod().equals(HttpMethod.GET.name())) {
            return handleGet();
        } else if (request.getMethod().equals(HttpMethod.POST.name())) {
            return handlePost(request);
        } else if (request.getMethod().equals(HttpMethod.DELETE.name())) {
            return handleDelete(request);
        }
        return notFound();
    }

    private ResponseHandler handleGet() {
        List<TradingDTO> tradingDTOs = tradingService
                .findAll()
                .stream()
                .map(TradingDeal::createDTO)
                .collect(toList());

        ResponseHandler rh = ok();
        rh.setContent(new Gson().toJson(tradingDTOs));
        rh.setContentType(APPLICATION_JSON.getName());
        return rh;
    }

    private ResponseHandler handlePost(Request request) {
        String[] segments = request.getUrl().getSegments();
        if (segments.length == 1) {
            return handlePostCreate(request);
        } else if (segments.length == 2) {
            return handlePostTrade(request);
        } else {
            return notFound();
        }
    }

    private ResponseHandler handlePostCreate(Request request) {
        String token = UserUtil.extractToken(request.getHeaders().get(AUTHORIZATION.getName()));
        TradingDTO tradingDTO = new GsonBuilder().create().fromJson(request.getContentString(), TradingDTO.class);
        User user = new UserService().findByUsername(UserManager.getUsernameForToken(token));
        try {
            Card cardToTrade = user.getCards()
                    .stream()
                    .filter(card -> card.getId().equals(tradingDTO.getCardToTradeId()))
                    .findFirst()
                    .orElseThrow(EntityNotFoundException::new);

            user.lockCard(cardToTrade);
            TradingDeal tradingDeal = new TradingDeal(tradingDTO.getId(), cardToTrade, cardToTrade.getType(), tradingDTO.getMinimumDamage(), user);
            tradingService.create(tradingDeal);
            userService.update(user);
            return noContent();
        } catch (EntityNotFoundException e) {
            return badRequest("User " + user.getUsername() + " doesn't possess this card");
        }
    }

    private ResponseHandler handlePostTrade(Request request) {
        String token = UserUtil.extractToken(request.getHeaders().get(AUTHORIZATION.getName()));
        String username = UserManager.getUsernameForToken(token);
        TradingDeal deal = tradingService.findById(request.getUrl().getSegments()[1]);
        if (deal.getCreator().getUsername().equals(username)) {
            return badRequest("Can't trade with yourself!");
        } else {
            String dealAcceptersCardId = new Gson().fromJson(request.getContentString(), String.class);
            return processTrade(deal, username, dealAcceptersCardId);
        }
    }

    private ResponseHandler processTrade(TradingDeal deal, String username, String dealAcceptersCardId) {
        User dealCreator = userService.findByUsername(deal.getCreator().getUsername());

        User dealAccepter = userService.findByUsername(username);
        Card dealAcceptersCard = new CardService().findById(dealAcceptersCardId);

        if (dealAcceptersCard.getDamage() < deal.getMinimumDamage() || dealAcceptersCard.getType() != deal.getType()) {
            return badRequest("Offered card doesn't fit the trade requirements: minimum damage =" + deal.getMinimumDamage() +
                    ", type " + deal.getType());
        }

        dealCreator.unlockCard(deal.getCardToTrade());
        userService.transferCard(dealAccepter, dealCreator, dealAcceptersCard);
        userService.transferCard(dealCreator, dealAccepter, deal.getCardToTrade());
        tradingService.delete(deal.getId());
        return noContent();
    }

    private ResponseHandler handleDelete(Request request) {
        String[] segments = request.getUrl().getSegments();
        if (segments.length != 2) {
            return notFound();
        }
        String dealId = segments[1];
        TradingDeal tradingDeal = tradingService.findById(dealId);
        String token = UserUtil.extractToken(request.getHeaders().get(AUTHORIZATION.getName()));
        String username = UserUtil.extractUsernameFromToken(token);

        if (!tradingDeal.getCreator().getUsername().equals(username)) {
            return forbidden();
        }
        User user = userService.findByUsername(username);
        user.unlockCard(tradingDeal.getCardToTrade());
        userService.update(user);
        tradingService.delete(dealId);
        return noContent();
    }
}
