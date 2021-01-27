package at.fhtw.bif3.db.dao;

import at.fhtw.bif3.domain.User;
import at.fhtw.bif3.exception.DBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO<User, String> {

    private final String tableName = "player";

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO " + getTableName() + " (player_username, player_password, player_number_of_coins, player_name, player_bio, player_image, player_games_played, player_elo, player_id)" +
                " VALUES (?,?,?,?,?,?,?,?,?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET player_username = ?, player_password = ?, player_number_of_coins = ?, player_name = ?, player_bio = ?, " +
                "player_image = ?, player_games_played = ?, player_elo = ? WHERE player_id = ?;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, User user) throws DBException {
        try {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getNumberOfCoins());
            statement.setString(4, user.getName());
            statement.setString(5, user.getBio());
            statement.setString(6, user.getImage());
            statement.setInt(7, user.getGamesPlayed());
            statement.setInt(8, user.getElo());
            statement.setString(9, user.getId());
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    @Override
    protected User readObject(ResultSet resultSet) throws DBException {
        User player = new User();
        try {
            player.setId(resultSet.getString("player_id"));
            player.setUsername(resultSet.getString("player_username"));
            player.setPassword(resultSet.getString("player_password"));
            player.setNumberOfCoins(resultSet.getInt("player_number_of_coins"));
            player.setName(resultSet.getString("player_name"));
            player.setBio(resultSet.getString("player_bio"));
            player.setImage(resultSet.getString("player_image"));
            player.setGamesPlayed(resultSet.getInt("player_games_played"));
            player.setElo(resultSet.getInt("player_elo"));
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
        return player;
    }

    @Override
    public void delete(String userId) throws DBException {
        super.delete(userId);
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}