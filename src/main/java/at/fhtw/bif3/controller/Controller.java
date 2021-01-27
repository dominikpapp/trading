package at.fhtw.bif3.controller;

import at.fhtw.bif3.http.request.Request;
import at.fhtw.bif3.http.response.ResponseHandler;

import static at.fhtw.bif3.http.HttpStatusCode.*;

public interface Controller {

    ResponseHandler handleRequest(Request request);

    default ResponseHandler ok() {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(OK.getCode());
        return responseHandler;
    }

    default ResponseHandler created() {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(CREATED.getCode());
        return responseHandler;
    }

    default ResponseHandler accepted() {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(ACCEPTED.getCode());
        return responseHandler;
    }

    default ResponseHandler noContent() {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(NO_CONTENT.getCode());
        return responseHandler;
    }

    default ResponseHandler notFound() {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(NOT_FOUND.getCode());
        return responseHandler;
    }

    default ResponseHandler forbidden() {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(FORBIDDEN.getCode());
        return responseHandler;
    }

    default ResponseHandler internalServerError(String message) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(INTERNAL_SERVER_ERROR.getCode());
        responseHandler.setContent(message);
        return responseHandler;
    }

    default ResponseHandler badRequest() {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(BAD_REQUEST.getCode());
        return responseHandler;
    }

    default ResponseHandler badRequest(String message) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(BAD_REQUEST.getCode());
        responseHandler.setContent(message);
        return responseHandler;
    }

    default ResponseHandler unauthorized(String message) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(UNAUTHORIZED.getCode());
        responseHandler.setContent(message);
        return responseHandler;
    }

    default ResponseHandler notImplemented(String message) {
        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setStatusCode(NOT_IMPLEMENTED.getCode());
        responseHandler.setContent(message);
        return responseHandler;
    }


}
