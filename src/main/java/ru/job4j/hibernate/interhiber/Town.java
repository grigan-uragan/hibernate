package ru.job4j.hibernate.interhiber;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Town() {
    }

    public Town(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Town town = (Town) o;
        return id == town.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
