package ru.job4j.hibernate.lazy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "automodels")
public class AutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;

    public static AutoModel of(String name) {
        AutoModel autoModel = new AutoModel();
        autoModel.name = name;
        return autoModel;
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

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AutoModel autoModel = (AutoModel) o;
        return id == autoModel.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AutoModel{" + "id="
                + id + ", name='"
                + name + '\'' + '}';
    }
}
