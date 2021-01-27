package at.fhtw.bif3.db.dao;

import at.fhtw.bif3.db.controller.ConnectionController;
import at.fhtw.bif3.exception.DBException;
import at.fhtw.bif3.exception.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T, K> implements GenericDAO<T, K> {

    protected abstract String getCreateQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getTableName();

    protected abstract void setObjectStatement(PreparedStatement preparedStatement, T object) throws DBException;

    protected abstract T readObject(ResultSet resultSet) throws DBException;

    protected String getCountRowsQuery() {
        return "SELECT count(*) FROM " + getTableName();
    }

    protected String getSelectByIdQuery() {
        return "SELECT * FROM " + getTableName() + " WHERE " + getTableName() + "_id = ?";
    }

    protected String getDeleteQuery() {
        return "DELETE FROM " + getTableName() + " WHERE " + getTableName() + "_id = ?";
    }


    @Override
    public boolean create(T object) throws DBException {
        String createQuery = getCreateQuery();
        try (Connection connection = ConnectionController.getConnection();
             PreparedStatement statement = connection.prepareStatement(createQuery)) {
            setObjectStatement(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public T read(K id) throws DBException {
        T object;
        String selectByIdQuery = getSelectByIdQuery();
        try (Connection connection = ConnectionController.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectByIdQuery)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                object = readObject(resultSet);
            } else {
                throw new EntityNotFoundException("Could not find an entity with id=" + id);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return object;
    }

    public T findByField(String fieldName, String fieldValue) throws DBException {
        T object;
        String query = "SELECT * FROM " + getTableName() + " WHERE " + fieldName + " = ?;";

        try (Connection connection = ConnectionController.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, fieldValue);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                object = readObject(resultSet);
            } else {
                throw new EntityNotFoundException("Could not find an object with " + fieldName + " = " + fieldValue + "...");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return object;
    }

    @Override
    public void update(T object) throws DBException {
        try (Connection connection = ConnectionController.getConnection();
             PreparedStatement statement = connection.prepareStatement(getUpdateQuery())) {
            setObjectStatement(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(K id) throws DBException {
        try (Connection connection = ConnectionController.getConnection();
             PreparedStatement statement = connection.prepareStatement(getDeleteQuery())) {
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    public int countEntities() throws DBException {
        String countRowsQuery = getCountRowsQuery();
        try (Connection connection = ConnectionController.getConnection();
             PreparedStatement statement = connection.prepareStatement(countRowsQuery)) {

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new DBException("An error has occured while counting the entities...");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public T findRandom() {
        T object;
        try (Connection connection = ConnectionController.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM " + getTableName() + " ORDER BY RANDOM() LIMIT 1;";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                object = readObject(resultSet);
            } else {
                throw new EntityNotFoundException("Could not find a result in the table '" + getTableName() + "'...");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return object;
    }

    public List<T> findAll() {
        List<T> objects = new ArrayList<>();
        try (Connection connection = ConnectionController.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM " + getTableName() + ";";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                objects.add(readObject(resultSet));
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return objects;
    }
}

