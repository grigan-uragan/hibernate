package ru.job4j.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.hibernate.model.Car;
import ru.job4j.hibernate.model.Model;

public class CarsHbnRun {
    private static final Logger LOG = LoggerFactory.getLogger(CarsHbnRun.class);

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            SessionFactory factory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            Session session = factory.openSession();
            session.beginTransaction();
            Car ford = new Car("Ford");
            Model focus = new Model("Focus");
            Model mustang = new Model("Mustang");
            Model fiesta = new Model("Fiesta");
            Model transit = new Model("Transit");
            Model kuga = new Model("Kuga");
            ford.addModel(focus);
            ford.addModel(mustang);
            ford.addModel(fiesta);
            ford.addModel(transit);
            ford.addModel(kuga);
            session.save(ford);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("hibernate exception", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
