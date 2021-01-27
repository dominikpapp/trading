package at.fhtw.bif3.db.dao;

import at.fhtw.bif3.domain.TradingDeal;
import at.fhtw.bif3.domain.card.CardType;
import at.fhtw.bif3.exception.DBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TradingDealDAO extends AbstractDAO<TradingDeal, String> {

    private final String tableName = "trading_deal";

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO " + getTableName() + "(trading_deal_card_id, trading_deal_card_type, trading_deal_minimum_damage, trading_deal_creator_id, trading_deal_id) VALUES (?,?,?,?,?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET trading_deal_card_id = ?, trading_deal_card_type = ?, trading_deal_minimum_damage = ?, trading_deal_creator_id = ? WHERE trading_deal_id = ?;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, TradingDeal tradingDeal) throws DBException {
        try {
            statement.setString(1, tradingDeal.getCardToTrade().getId());
            statement.setString(2, tradingDeal.getType().name());
            statement.setDouble(3, tradingDeal.getMinimumDamage());
            statement.setString(4, tradingDeal.getCreator().getId());
            statement.setString(5, tradingDeal.getId());
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    @Override
    protected TradingDeal readObject(ResultSet resultSet) throws DBException {
        TradingDeal tradingDeal = new TradingDeal();
        try {
            tradingDeal.setId(resultSet.getString("trading_deal_id"));
            tradingDeal.setCardToTrade(new CardDAO().read(resultSet.getString("trading_deal_card_id")));
            tradingDeal.setType(CardType.assignByName(resultSet.getString("trading_deal_card_type")));
            tradingDeal.setMinimumDamage(resultSet.getDouble("trading_deal_minimum_damage"));
            tradingDeal.setCreator(new UserDAO().read(resultSet.getString("trading_deal_creator_id")));
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
        return tradingDeal;
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
