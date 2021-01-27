package at.fhtw.bif3.domain;

import at.fhtw.bif3.domain.card.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Bundle {
    private String id;
    private List<Card> cards = new ArrayList<>();

    public Bundle() {
    }

    public Bundle(String id, List<Card> cards) {
        this.id = id;
        this.cards = cards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bundle bundle = (Bundle) o;
        return Objects.equals(id, bundle.id) &&
                Objects.equals(cards.size(), bundle.cards.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cards);
    }
}
