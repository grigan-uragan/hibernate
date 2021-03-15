package ru.job4j.hibernate.car;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car")
public class Ford {
    // переименовал на Ford потому что Hibernate
    // ругался на то что уже есть сущность с таким именем в другом пакете
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private Engine engine;

    public static Ford of(String name, Engine engine) {
        Ford car = new Ford();
        car.name = name;
        car.engine = engine;
        return car;
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

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ford ford = (Ford) o;
        return id == ford.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
