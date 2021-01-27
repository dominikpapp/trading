package at.fhtw.bif3.db.dao;

import at.fhtw.bif3.domain.card.Card;
import at.fhtw.bif3.domain.card.CardClass;
import at.fhtw.bif3.domain.card.ElementType;
import at.fhtw.bif3.domain.card.SpellCard;
import at.fhtw.bif3.exception.DBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardDAO extends AbstractDAO<Card, String> {

    private final String tableName = "card";

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO " + getTableName() + " (card_name, card_damage, card_weakness, card_element_type, card_type, card_id) VALUES (?,?,?,?,?,?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET card_name = ?, card_damage = ?, card_weakness = ?, card_element_type = ?, card_type = ? WHERE card_id = ?;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, Card card) throws DBException {
        try {
            statement.setString(1, card.getName());
            statement.setDouble(2, card.getDamage());
            statement.setDouble(3, card instanceof SpellCard ? ((SpellCard) card).getWeakness() : 0);
            statement.setString(4, card.getElementType().name());
            statement.setString(5, card.getCardClass().name());
            statement.setString(6, card.getId());
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    @Override
    protected Card readObject(ResultSet resultSet) throws DBException {
        Card card;
        try {
            CardClass cardClass = CardClass.assignByName(resultSet.getString("card_type"));
            card = cardClass.instantiateByType();

            card.setId(resultSet.getString("card_id"));
            card.setName(resultSet.getString("card_name"));
            card.setDamage(resultSet.getDouble("card_damage"));
            if (card instanceof SpellCard) {
                ((SpellCard) card).setWeakness(resultSet.getDouble("card_weakness"));
            }
            card.setElementType(ElementType.assignByName(resultSet.getString("card_element_type")));

        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
        return card;
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}

