package at.fhtw.bif3.domain.card;

import at.fhtw.bif3.util.ApplicationPropertiesReader;

import static java.lang.Integer.parseInt;

public class OrkCard extends Card {

    public static final int DAMAGE_AGAINST_WIZARD = parseInt(ApplicationPropertiesReader.getINSTANCE().getProperty("mtcg.damage.ork-vs-wizard"));

    public OrkCard() {
    }

    public OrkCard(String id, String name, double damage, ElementType elementType) {
        super(id, name, damage, elementType);
    }

    @Override
    public double calculateDamageAgainst(Card card) {
        if (card instanceof WizardCard) {
            return DAMAGE_AGAINST_WIZARD;
        }
        return super.calculateDamageAgainst(card);
    }

    @Override
    protected void setCardClass() {
        this.cardClass = CardClass.ORK;
    }
}