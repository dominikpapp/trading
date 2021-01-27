package at.fhtw.bif3.domain.card;

public class FireElfCard extends Card {
    public FireElfCard() {
    }

    public FireElfCard(String id, String name, double damage, ElementType elementType) {
        super(id, name, damage, elementType);
    }

    @Override
    protected void setCardClass() {
        this.cardClass = CardClass.FIRE_ELF;
    }
}
