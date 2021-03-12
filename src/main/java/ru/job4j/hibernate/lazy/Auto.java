package ru.job4j.hibernate.lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "autos")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "auto")
    private List<AutoModel> autoModels = new ArrayList<>();

    public static Auto of(String name) {
        Auto auto = new Auto();
        auto.name = name;
        return auto;
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

    public List<AutoModel> getAutoModels() {
        return autoModels;
    }

    public void setAutoModels(List<AutoModel> autoModels) {
        this.autoModels = autoModels;
    }

    public void addModel(AutoModel model) {
        autoModels.add(model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Auto auto = (Auto) o;
        return id == auto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
