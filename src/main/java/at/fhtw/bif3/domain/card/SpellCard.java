package at.fhtw.bif3.domain.card;

import com.google.gson.annotations.SerializedName;

import static at.fhtw.bif3.domain.card.CardClass.SPELL;

public class SpellCard extends Card {

    @SerializedName("Weakness")
    private double weakness;

    public SpellCard() {
    }

    public SpellCard(String id, String name, double damage, ElementType elementType) {
        super(id, name, damage, elementType);
    }


    @Override
    protected void setCardClass() {
        this.cardClass = SPELL;
    }

    public double getWeakness() {
        return weakness;
    }

    public void setWeakness(double weakness) {
        this.weakness = weakness;
    }
}

