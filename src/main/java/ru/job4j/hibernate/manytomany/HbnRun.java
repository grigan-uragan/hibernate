package ru.job4j.hibernate.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbnRun {
    private static final Logger LOG = LoggerFactory.getLogger(HbnRun.class);

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
            Address one = Address.of("Tversckaya", "11");
            Address two = Address.of("Lenina", "14");
            Person nick = Person.of("Nikolay");
            nick.addAddress(one);
            nick.addAddress(two);
            Person bob = Person.of("Bob");
            bob.addAddress(one);
            session.persist(nick);
            session.persist(bob);
            Person person = session.get(Person.class, 1);
            session.remove(person);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("hibernate exception", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
