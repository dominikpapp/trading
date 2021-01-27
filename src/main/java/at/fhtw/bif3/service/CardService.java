package at.fhtw.bif3.service;

import at.fhtw.bif3.db.dao.CardDAO;
import at.fhtw.bif3.domain.card.Card;

public class CardService extends AbstractService<Card, String> {
    public CardService() {
        super(new CardDAO());
    }

    public void delete(Card card) {
        delete(card.getId());
    }
}

