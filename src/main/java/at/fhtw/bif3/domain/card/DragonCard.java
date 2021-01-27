package at.fhtw.bif3.domain.card;

public class DragonCard extends Card {

    public DragonCard() {
    }

    public DragonCard(String id, String name, double damage, ElementType elementType) {
        super(id, name, damage, elementType);
    }

    @Override
    protected void setCardClass() {
        this.cardClass = CardClass.DRAGON;
    }
}
