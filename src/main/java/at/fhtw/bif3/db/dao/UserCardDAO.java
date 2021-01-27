package at.fhtw.bif3.db.dao;

import at.fhtw.bif3.db.controller.ConnectionController;
import at.fhtw.bif3.db.dao.entity.PlayerCard;
import at.fhtw.bif3.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCardDAO extends AbstractDAO<PlayerCard, String> {

    private final String tableName;

    public UserCardDAO(String tableName) {
        super();
        this.tableName = tableName;
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO " + getTableName() + " (player_id, card_id) VALUES (?,?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET player_id = ?, card_id = ? WHERE player_id = ? AND card_id = ?;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, PlayerCard playerCard) throws DBException {
        try {
            statement.setString(1, playerCard.getPlayerId());
            statement.setString(2, playerCard.getCardId());
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    @Override
    protected PlayerCard readObject(ResultSet resultSet) throws DBException {
        PlayerCard playerCard = new PlayerCard();
        try {
            playerCard.setPlayerId(resultSet.getString("player_id"));
            playerCard.setCardId(resultSet.getString("card_id"));
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
        return playerCard;
    }

    public List<PlayerCard> findAllByUserId(String id) throws DBException {
        String query = "select * from " + getTableName() + " where player_id  = ?;";

        List<PlayerCard> playerCards = new ArrayList<>();
        try (Connection connection = ConnectionController.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String cardId = resultSet.getString("card_id");
                playerCards.add(new PlayerCard(id, cardId));
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
        return playerCards;
    }

    public void deleteByPlayerId(String id) {
        String query = "delete from " + getTableName() + " where player_id  = ?;";
        try (Connection connection = ConnectionController.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
