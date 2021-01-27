package at.fhtw.bif3.domain.card;

import java.util.Optional;


public enum CardClass {
    SPELL,
    GOBLIN,
    DRAGON,
    WIZARD,
    ORK,
    KNIGHT,
    KRAKEN,
    FIRE_ELF;

    public static CardClass assignByName(String name) {
        if ("FireElf".equals(name)) {
            name = "FIRE_ELF";
        }

        for (CardClass value : values()) {
            if (name.toLowerCase().contains(value.name().toLowerCase())) {
                return Optional.of(value)
                        .orElseThrow();
            }
        }
        return Optional.<CardClass>empty().orElseThrow();
    }

    public Card instantiateByType() {
        if (this.equals(SPELL)) {
            return new SpellCard();
        } else if (this.equals(GOBLIN)) {
            return new GoblinCard();
        } else if (this.equals(DRAGON)) {
            return new DragonCard();
        } else if (this.equals(WIZARD)) {
            return new WizardCard();
        } else if (this.equals(ORK)) {
            return new OrkCard();
        } else if (this.equals(KNIGHT)) {
            return new KnightCard();
        } else if (this.equals(KRAKEN)) {
            return new KrakenCard();
        }
        return new FireElfCard();
    }

    public boolean isSpellClass() {
        return this.equals(SPELL);
    }
}
