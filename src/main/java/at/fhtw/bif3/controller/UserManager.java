package at.fhtw.bif3.controller;

import at.fhtw.bif3.util.ApplicationPropertiesReader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserManager {

    private static final ConcurrentMap<String, String> loggedUsers = new ConcurrentHashMap<>();

    public static boolean isUserLoggedIn(String username) {
        return loggedUsers.keySet().stream().anyMatch(username::equals);
    }

    public static boolean tokenNotPresent(String token) {
        return loggedUsers.values().stream().noneMatch(token::equals);
    }

    public static void loginUser(String username) {
        loggedUsers.putIfAbsent(username, getTokenForUsername(username));
    }

    private static String getTokenForUsername(String username) {
        return username + ApplicationPropertiesReader.getINSTANCE().getProperty("mtcg.application.security.token");
    }

    public static void logoutUser(String username) {
        loggedUsers.remove(username);
    }

    public static String getUsernameForToken(String token) {
        return loggedUsers
                .entrySet()
                .stream()
                .filter(entry -> token.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
    }
}

