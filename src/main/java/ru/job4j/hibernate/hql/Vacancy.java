package ru.job4j.hibernate.hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private double royalty;

    public static Vacancy of(String description, double royalty) {
        Vacancy vacancy = new Vacancy();
        vacancy.description = description;
        vacancy.royalty = royalty;
        return vacancy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRoyalty() {
        return royalty;
    }

    public void setRoyalty(double royalty) {
        this.royalty = royalty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vacancy{" + "id="
                + id + ", description='"
                + description + '\'' + ", royalty="
                + royalty + '}';
    }
}
