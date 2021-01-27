package at.fhtw.bif3.db.dao.entity;

public class PlayerCard {
    private String playerId;
    private String cardId;

    public PlayerCard() {
    }

    public PlayerCard(String playerId, String cardId) {
        this.playerId = playerId;
        this.cardId = cardId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}

