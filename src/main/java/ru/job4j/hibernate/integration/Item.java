package ru.job4j.hibernate.integration;

import java.sql.Timestamp;
import java.util.Objects;

public class Item {
    private int id;
    private String name;
    private String description;
    private Timestamp created;

    public Item() {
    }

    public Item(int id, String name, String description, Timestamp created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public static Item of(String name, String description) {
        Item item = new Item();
        item.name = name;
        item.description = description;
        return item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
