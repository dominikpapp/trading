package at.fhtw.bif3.service;

import at.fhtw.bif3.db.dao.UserCardDAO;
import at.fhtw.bif3.db.dao.UserDAO;
import at.fhtw.bif3.db.dao.entity.PlayerCard;
import at.fhtw.bif3.domain.Bundle;
import at.fhtw.bif3.domain.User;
import at.fhtw.bif3.domain.card.Card;
import at.fhtw.bif3.exception.EntityNotFoundException;
import at.fhtw.bif3.exception.NotEnoughCoinsException;
import at.fhtw.bif3.exception.TransactionProcessingException;
import at.fhtw.bif3.util.ApplicationPropertiesReader;

import static java.lang.Integer.parseInt;


public class UserService extends AbstractService<User, String> {
    public static final int CARD_PACKAGE_PRICE = parseInt(ApplicationPropertiesReader.getINSTANCE().getProperty("mtcg.package.price"));
    public static final String PLAYER_CARD_TABLE = "player_card";

    private final UserCardDAO userCardDAO = new UserCardDAO(PLAYER_CARD_TABLE);
    private final CardService cardService = new CardService();


    public UserService() {
        super(new UserDAO());
    }

    public void save(User player) {
        if (!exists(player.getId())) {
            create(player);
        } else {
            update(player);
        }
    }

    @Override
    public void create(User user) {
        super.create(user);
        persistCardsForUser(user);
    }

    @Override
    public void update(User user) {
        super.update(user);
        userCardDAO.deleteByPlayerId(user.getId());
        persistCardsForUser(user);
    }

    private void persistCardsForUser(User user) {
        user.getCards().forEach(card -> userCardDAO.create(new PlayerCard(user.getId(), card.getId())));
    }

    public void delete(User user) {
        user.getCards().forEach(cardService::delete);
        super.delete(user.getId());
    }

    @Override
    public User findById(String id) {
        User user = super.findById(id);
        readCardsForUser(user);
        return user;
    }

    public User findByUsername(String username) {
        User user = findByField("player_username", username);
        readCardsForUser(user);
        return user;
    }

    public synchronized void processPackagePurchaseFor(String username) {
        User user = findByUsername(username);
        if (user.getNumberOfCoins() < CARD_PACKAGE_PRICE) {
            throw new NotEnoughCoinsException(username);
        }

        int amountOfMoneyBeforeTransaction = user.getNumberOfCoins();
        try {
            BundleService bundleService = new BundleService();
            Bundle bundle = bundleService.
                    findRandom();
            user.setNumberOfCoins(user.getNumberOfCoins() - CARD_PACKAGE_PRICE);
            bundle.getCards().forEach(user::addCard);
            update(user);
            bundleService.delete(bundle.getId());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("There are no packages available for purchase");
        } catch (Exception e) {
            user.setNumberOfCoins(amountOfMoneyBeforeTransaction);
            e.printStackTrace();
            throw new TransactionProcessingException("The purchase failed. The transaction has been rolled back.");
        }
    }

    private void readCardsForUser(User user) {
        userCardDAO.findAllByUserId(user.getId())
                .stream()
                .map(playerCard -> cardService.findById(playerCard.getCardId()))
                .forEach(user::addCard);
    }

    public void transferCard(User giver, User receiver, Card card) {
        giver.removeCard(card);
        receiver.addCard(card);

        update(giver);
        update(receiver);
    }
}
