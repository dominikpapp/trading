package at.fhtw.bif3.db.dao;

import at.fhtw.bif3.db.controller.ConnectionController;
import at.fhtw.bif3.db.dao.entity.BundleCard;
import at.fhtw.bif3.domain.card.Card;
import at.fhtw.bif3.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BundleCardDAO extends AbstractDAO<BundleCard, String> {

    private final String tableName = "bundle_card";

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO " + getTableName() + " (bundle_card_bundle_id, bundle_card_card_id) VALUES (?,?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET bundle_card_bundle_id = ?, bundle_card_card_id = ? WHERE bundle_card_bundle_id = ? AND bundle_card_card_id = ?;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, BundleCard bundleCard) throws DBException {
        try {
            statement.setString(1, bundleCard.getBundleId());
            statement.setString(2, bundleCard.getCardId());
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    @Override
    protected BundleCard readObject(ResultSet resultSet) throws DBException {
        BundleCard bundleCard = new BundleCard();
        try {
            bundleCard.setBundleId(resultSet.getString("bundle_card_bundle_id"));
            bundleCard.setCardId(resultSet.getString("bundle_card_card_id"));
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
        return bundleCard;
    }

    protected List<Card> findAllByBundleId(String id) throws DBException {
        String query = "select * from " + getTableName() + " where bundle_card_bundle_id  = ?;";
        return findAllByQuery(id, query);
    }

    protected List<Card> findAllByQuery(String id, String query) throws DBException {
        List<Card> cards = new ArrayList<>();
        try (Connection connection = ConnectionController.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            CardDAO cardDAO = new CardDAO();
            while (resultSet.next()) {
                String cardId = resultSet.getString("bundle_card_card_id");
                cards.add(cardDAO.read(cardId));
            }
        } catch (SQLException | DBException e) {
            throw new DBException(e.getMessage(), e);
        }
        return cards;
    }

    public void deleteByBundleId(String bundleId) throws DBException {
        String query = "delete from " + getTableName() + " where bundle_card_bundle_id  = ?;";

        try (Connection connection = ConnectionController.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bundleId);
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
