package at.fhtw.bif3.domain;

import at.fhtw.bif3.controller.dto.TradingDTO;
import at.fhtw.bif3.domain.card.Card;
import at.fhtw.bif3.domain.card.CardType;
import com.google.gson.annotations.SerializedName;


public class TradingDeal {
    @SerializedName("Id")
    private String id;
    @SerializedName("CardToTrade")
    private Card cardToTrade;
    @SerializedName("Type")
    private CardType type;
    @SerializedName("MinimumDamage")
    private double minimumDamage;
    private User creator;

    public TradingDeal() {
    }

    public TradingDeal(String id, Card cardToTrade, CardType type, double minimumDamage, User creator) {
        this.id = id;
        this.cardToTrade = cardToTrade;
        this.type = type;
        this.minimumDamage = minimumDamage;
        this.creator = creator;
    }

    public static TradingDTO createDTO(TradingDeal tradingDeal) {
        return new TradingDTO(tradingDeal.getId(), tradingDeal.getCardToTrade().getId(),
                tradingDeal.getType().name(), tradingDeal.getMinimumDamage());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Card getCardToTrade() {
        return cardToTrade;
    }

    public void setCardToTrade(Card cardToTrade) {
        this.cardToTrade = cardToTrade;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public double getMinimumDamage() {
        return minimumDamage;
    }

    public void setMinimumDamage(double minimumDamage) {
        this.minimumDamage = minimumDamage;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
