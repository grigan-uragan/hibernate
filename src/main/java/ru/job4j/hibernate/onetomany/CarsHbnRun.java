package ru.job4j.hibernate.onetomany;

import org.hibernate.Session;
import ru.job4j.hibernate.util.HibernateUtil;

public class CarsHbnRun {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
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
    }
}
