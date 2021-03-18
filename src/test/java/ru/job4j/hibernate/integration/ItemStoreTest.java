package ru.job4j.hibernate.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ItemStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("postgres");
        pool.setPassword("password");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("db/update_001.sql"))) {
            reader.lines().forEach(l -> builder.append(l).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void clear() throws SQLException{
        pool.getConnection().prepareStatement("drop table items if exists ").executeUpdate();
    }

    @Test
    public void whenSaveItemThenFindAllReturnOneItem() {
        ItemStore store = new ItemStore(pool);
        Item item = Item.of("first item", "for tests");
        Item expected = store.save(item);
        Item result = store.findAll().get(0);
        assertThat(result, is(expected));
    }

    @Test
    public void whenSaveItemThenItemIdChange() {
        ItemStore store = new ItemStore(pool);
        Item item = Item.of("first item", "for tests");
        assertThat(0, is(item.getId()));
        Item expected = store.save(item);
        assertThat(1, is(expected.getId()));
    }

    @Test
    public void whenSaveItemThenFindByIdReturnItem() {
        ItemStore store = new ItemStore(pool);
        Item item = Item.of("first item", "for tests");
        Item save = store.save(item);
        Item byId = store.findItemById(1);
        assertThat(save, is(byId));
    }

}