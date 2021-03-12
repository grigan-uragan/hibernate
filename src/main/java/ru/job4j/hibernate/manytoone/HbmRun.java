package ru.job4j.hibernate.manytoone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.hibernate.util.HibernateUtil;

import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        User one = User.of("First");
        User two = User.of("Second");
        Role role = Role.of("ADMIN");
        role.addUser(one);
        role.addUser(two);
        create(role, factory);
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
