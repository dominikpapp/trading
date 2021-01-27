package at.fhtw.bif3.service;

import at.fhtw.bif3.db.dao.BundleCardDAO;
import at.fhtw.bif3.db.dao.BundleDAO;
import at.fhtw.bif3.db.dao.entity.BundleCard;
import at.fhtw.bif3.domain.Bundle;
import at.fhtw.bif3.exception.DBException;

public class BundleService extends AbstractService<Bundle, String> {
    private final BundleCardDAO bundleCardDAO = new BundleCardDAO();

    public BundleService() {
        super(new BundleDAO());
    }

    @Override
    public void create(Bundle bundle) {
        super.create(bundle);
        bundle.getCards()
                .stream()
                .map(card -> new BundleCard(bundle.getId(), card.getId()))
                .forEach(object -> {
                    try {
                        bundleCardDAO.create(object);
                    } catch (DBException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void update(Bundle bundle) {
        bundleCardDAO.deleteByBundleId(bundle.getId());
        bundle.getCards()
                .stream()
                .map(card -> new BundleCard(bundle.getId(), card.getId()))
                .forEach(bundleCardDAO::create);
    }

    public void deleteWithCards(String bundle_id) {
        Bundle bundle = findById(bundle_id);
        CardService cardService = new CardService();
        bundle.getCards()
                .forEach(cardService::delete);
        super.delete(bundle_id);
    }
}

