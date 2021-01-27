package at.fhtw.bif3.domain.card;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import static at.fhtw.bif3.domain.card.CardType.MONSTER;
import static at.fhtw.bif3.domain.card.CardType.SPELL;

public abstract class Card {
    @SerializedName("Id")
    String id;

    @SerializedName("Name")
    String name;

    @SerializedName("Damage")
    double damage;

    ElementType elementType;

    CardClass cardClass;

    CardType type;

    {
        setCardClass();
        if (cardClass.isSpellClass()) {
            type = SPELL;
        } else {
            type = MONSTER;
        }
    }

    public Card() {
    }

    public Card(String id, String name, double damage, ElementType elementType) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
    }

    public Card(String id, String name, int damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
    }

    public double calculateDamageAgainst(Card card) {
        return damage;
    }

    protected abstract void setCardClass();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public CardClass getCardClass() {
        return cardClass;
    }

    public void setCardClass(CardClass cardClass) {
        this.cardClass = cardClass;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Double.compare(card.damage, damage) == 0 && id.equals(card.id) && name.equals(card.name) && elementType == card.elementType && cardClass == card.cardClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, damage, elementType, cardClass);
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", damage=" + damage +
                ", elementType=" + elementType +
                '}';
    }
}
