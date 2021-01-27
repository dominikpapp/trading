package at.fhtw.bif3.domain;

import at.fhtw.bif3.domain.card.Card;
import at.fhtw.bif3.util.ApplicationPropertiesReader;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class User {

    public static final int DECK_SIZE = Integer.parseInt(ApplicationPropertiesReader.getINSTANCE().getProperty("mtcg.deck.battle.size"));
    @SerializedName("Id")
    private String id;
    @SerializedName("Username")
    private String username;
    @SerializedName("Password")
    private String password;
    @SerializedName("Name")
    private String name;
    @SerializedName("Bio")
    private String bio;
    @SerializedName("Image")
    private String image;
    private int gamesPlayed;
    private int elo = Integer.parseInt(ApplicationPropertiesReader.getINSTANCE().getProperty("mtcg.elo.start-value"));
    private int numberOfCoins = Integer.parseInt(ApplicationPropertiesReader.getINSTANCE().getProperty("mtcg.user.start-money"));
    private List<Card> cards = new ArrayList<>();
    private List<Card> deck = new ArrayList<>();
    private List<Card> lockedForTrade = new ArrayList<>();

    public User(String id, String username, String password, String name, String bio, String image, int gamesPlayed, int elo, int numberOfCoins, List<Card> cards, List<Card> deck, List<Card> lockedForTrade) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.image = image;
        this.gamesPlayed = gamesPlayed;
        this.elo = elo;
        this.numberOfCoins = numberOfCoins;
        this.cards = cards;
        this.deck = deck;
        this.lockedForTrade = lockedForTrade;
    }

    public User() {
    }

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this(username, username, password);
    }

    public void returnCardsFromDeck() {
        cards.addAll(deck);
        deck.clear();
    }

    public void configureDeck(List<String> cardIds) {
        returnCardsFromDeck();
        cards.stream()
                .filter(card -> cardIds.contains(card.getId()))
                .forEach(deck::add);

        deck.forEach(cards::remove);
    }

    public void lockCard(Card cardToTrade) {
    }

    public void unlockCard(Card cardToTrade) {
        lockedForTrade.remove(cardToTrade);
        cards.add(cardToTrade);
    }

    public void addCardToDeck(Card card) {
        deck.add(card);
    }

    public void addCardsToDeck(Card playingCard, Card defendingCard) {
        deck.addAll(Arrays.asList(playingCard, defendingCard));
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    public void incrementElo(int pointsForWin) {
        elo += pointsForWin;
    }

    public void decrementElo(int pointsForLoss) {
        elo -= pointsForLoss;
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }

    public void setNumberOfCoins(int numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getLockedForTrade() {
        return lockedForTrade;
    }

    public void setLockedForTrade(List<Card> lockedForTrade) {
        this.lockedForTrade = lockedForTrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id='").append(id).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", bio='").append(bio).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", gamesPlayed=").append(gamesPlayed);
        sb.append(", elo=").append(elo);
        sb.append(", numberOfCoins=").append(numberOfCoins);
        sb.append(", cards=").append(cards);
        sb.append(", deck=").append(deck);
        sb.append('}');
        return sb.toString();
    }
}