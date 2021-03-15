package ru.job4j.hibernate.car;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.hibernate.util.HibernateUtil;

public class HbmRun {
    private static final Logger LOG  = LoggerFactory.getLogger(HbmRun.class);

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            Engine diesel = Engine.of("Diesel");
            Engine gasoline = Engine.of("Gasoline");
            Ford car = Ford.of("Taureg", diesel);
            Ford focus = Ford.of("Focus", gasoline);
            Driver driver = Driver.of("Bob");
            driver.addCar(car);
            driver.addCar(focus);
            session.save(driver);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            LOG.error("transaction exception", e);
            factory.getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }
}
