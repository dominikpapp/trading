package at.fhtw.bif3.domain.card;


import static at.fhtw.bif3.domain.card.CardClass.GOBLIN;

public class GoblinCard extends Card {

    public GoblinCard() {
    }

    public GoblinCard(String id, String name, double damage, ElementType elementType) {
        super(id, name, damage, elementType);
    }

    @Override
    protected void setCardClass() {
        this.cardClass = GOBLIN;
    }
}
