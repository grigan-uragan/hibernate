package ru.job4j.hibernate.interhiber;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TownStore implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(TownStore.class);
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory factory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    public Town save(Town city) {
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            session.save(city);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("transaction exception", e);
            factory.getCurrentSession().getTransaction().rollback();
            throw e;
        }
        return city;
    }

    public List<Town> findAll() {
        List<Town> list = new ArrayList<>();
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            list = session.createQuery("from Town order by id").list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("transaction exception", e);
            factory.getCurrentSession().getTransaction().rollback();
            throw e;
        }
        return list;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
