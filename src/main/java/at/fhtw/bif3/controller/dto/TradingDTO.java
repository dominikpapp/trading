package at.fhtw.bif3.controller.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class TradingDTO {
    @SerializedName("Id")
    private String id;
    @SerializedName("CardToTrade")
    private String cardToTradeId;
    @SerializedName("Type")
    private String type;
    @SerializedName("MinimumDamage")
    private double minimumDamage;

    public TradingDTO() {
    }

    public TradingDTO(String id, String cardToTradeId, String type, double minimumDamage) {
        this.id = id;
        this.cardToTradeId = cardToTradeId;
        this.type = type;
        this.minimumDamage = minimumDamage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardToTradeId() {
        return cardToTradeId;
    }

    public void setCardToTradeId(String cardToTradeId) {
        this.cardToTradeId = cardToTradeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMinimumDamage() {
        return minimumDamage;
    }

    public void setMinimumDamage(double minimumDamage) {
        this.minimumDamage = minimumDamage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradingDTO that = (TradingDTO) o;
        return Double.compare(that.minimumDamage, minimumDamage) == 0 && id.equals(that.id) && cardToTradeId.equals(that.cardToTradeId) && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardToTradeId, type, minimumDamage);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TradingDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", cardToTradeId='").append(cardToTradeId).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", minimumDamage=").append(minimumDamage);
        sb.append('}');
        return sb.toString();
    }
}

