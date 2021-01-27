package at.fhtw.bif3.db.dao.entity;

import java.util.Objects;

public class BundleCard {
    private String bundleId;
    private String cardId;

    public BundleCard() {
    }

    public BundleCard(String bundleId, String cardId) {
        this.bundleId = bundleId;
        this.cardId = cardId;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BundleCard that = (BundleCard) o;
        return bundleId.equals(that.bundleId) && cardId.equals(that.cardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bundleId, cardId);
    }

    @Override
    public String toString() {
        return "BundleCard{" +
                "bundleId='" + bundleId + '\'' +
                ", cardId='" + cardId + '\'' +
                '}';
    }
}
