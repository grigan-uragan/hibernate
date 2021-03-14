package ru.job4j.hibernate.date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.hibernate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class HbmRun {
    private static final Logger LOG  = LoggerFactory.getLogger(HbmRun.class);

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        List<Product> product = new ArrayList<>();
        try {
            session.beginTransaction();
            product = session.createQuery("from Product ").list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("transaction exception", e);
            throw e;
        }
        product.forEach(System.out::println);
    }
}
