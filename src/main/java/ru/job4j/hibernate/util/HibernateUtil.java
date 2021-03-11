package ru.job4j.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.hibernate.model.Role;
import ru.job4j.hibernate.model.User;

import java.io.File;
import java.util.List;

public class HibernateUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateUtil.class);

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            SessionFactory factory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            Role role = create(Role.of("ADMIN"), factory);
            create(User.of("Grigory", role), factory);
            for (User user : findAll(User.class, factory)) {
                System.out.println(user.getId());
                System.out.println(user.getName());
                System.out.println(user.getRole());
            }
        } catch (Exception e) {
            LOG.error("hibernate exception", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(model);
        session.getTransaction().commit();
        session.close();
        return model;
    }

    public static <T> List<T> findAll(Class<T> cl, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<T> list = session.createQuery("from " + cl.getName(), cl).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
