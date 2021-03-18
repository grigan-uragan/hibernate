package ru.job4j.hibernate.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemStore {
    private static final Logger LOG = LoggerFactory.getLogger(ItemStore.class);
    private BasicDataSource pool;

    public ItemStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Item save(Item item) {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "insert into items (name, description, created) values (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setTimestamp(3, item.getCreated());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                item.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            LOG.error("sql exception", e);
            e.printStackTrace();
        }
        return item;
    }

    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from items")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(new Item(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("created")
                ));
            }
        } catch (SQLException e) {
            LOG.error("sqlexception", e);
            e.printStackTrace();
        }
        return result;
    }

    public Item findItemById(int id) {
        Item item = null;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select * from items where id = (?)")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               item = new Item(
                       resultSet.getInt("id"),
                       resultSet.getString("name"),
                       resultSet.getString("description"),
                       resultSet.getTimestamp("created")
               );
            }
        } catch (SQLException e) {
            LOG.error("sql exception", e);
            e.printStackTrace();
        }
        return item;
    }
}
