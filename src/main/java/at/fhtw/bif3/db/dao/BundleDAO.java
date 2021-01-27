package at.fhtw.bif3.db.dao;

import at.fhtw.bif3.domain.Bundle;
import at.fhtw.bif3.exception.DBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BundleDAO extends AbstractDAO<Bundle, String> {

    private final String tableName = "bundle";

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO " + getTableName() + " (bundle_id) VALUES (?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET bundle_id = ? WHERE bundle_id = ?;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, Bundle bundle) throws DBException {
        try {
            statement.setString(1, bundle.getId());
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    @Override
    protected Bundle readObject(ResultSet resultSet) throws DBException {
        Bundle bundle = new Bundle();
        try {
            String id = resultSet.getString("bundle_id");
            bundle.setId(id);
            bundle.setCards(new BundleCardDAO().findAllByBundleId(id));
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
        return bundle;
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
