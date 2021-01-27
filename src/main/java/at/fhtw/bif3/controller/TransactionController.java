package at.fhtw.bif3.controller;

import at.fhtw.bif3.exception.DBException;
import at.fhtw.bif3.exception.EntityNotFoundException;
import at.fhtw.bif3.exception.NotEnoughCoinsException;
import at.fhtw.bif3.http.request.HttpMethod;
import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.response.ResponseHandler;
import at.fhtw.bif3.service.UserService;
import at.fhtw.bif3.util.UserUtil;

import static at.fhtw.bif3.http.Header.AUTHORIZATION;

public class TransactionController implements Controller {

    public static final String TRANSACTIONS_PACKAGES_ENDPOINT = "/transactions/packages";
    private final UserService userService = new UserService();

    @Override
    public ResponseHandler handleRequest(Request request) {
        if (request.getMethod().equals(HttpMethod.POST.name())) {
            return handlePost(request);
        }
        return notFound();
    }

    private ResponseHandler handlePost(Request request) {
        if (request.getUrl().getPath().equals(TRANSACTIONS_PACKAGES_ENDPOINT)) {
            return handleTransactionsPackagesPost(request);
        }
        return notFound();
    }

    private ResponseHandler handleTransactionsPackagesPost(Request request) {
        String token = UserUtil.extractToken(request.getHeaders().get(AUTHORIZATION.getName()));
        if (UserManager.tokenNotPresent(token)) {
            return forbidden();
        }

        try {
            String username = UserManager.getUsernameForToken(token);
            userService.processPackagePurchaseFor(username);
            return noContent();
        } catch (NotEnoughCoinsException e) {
            return badRequest(e.getMessage());
        } catch (DBException | EntityNotFoundException e) {
            return internalServerError(e.getMessage());
        }
    }
}

