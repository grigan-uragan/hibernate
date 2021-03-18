package ru.job4j.hibernate.interhiber;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TownStoreTest {

    @Test
    public void whenSaveTownThenReturnTown() {
        try (TownStore store = new TownStore()) {
            Town town = new Town("Moscow");
            store.save(town);
            List<Town> allTown = store.findAll();
            assertThat(town, is(allTown.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenSaveManyTownThenReturnAll() {
        try (TownStore store = new TownStore()) {
            Town one = new Town("Moscow");
            Town two = new Town("Saint-Petersburg");
            store.save(one);
            store.save(two);
            List<Town> allTown = store.findAll();
            assertThat(List.of(one, two), is(allTown));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}