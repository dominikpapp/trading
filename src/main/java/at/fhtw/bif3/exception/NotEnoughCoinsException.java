package at.fhtw.bif3.exception;

public class NotEnoughCoinsException extends RuntimeException {
    public NotEnoughCoinsException(String username) {
        super("User with username " + username + " doesn't have enough coins for this transaction");
    }
}

