package ru.job4j.hibernate.lazy;

import org.hibernate.Session;
import ru.job4j.hibernate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class AutoRun {
    public static void main(String[] args) {
        List<Auto> result = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        result = session.createQuery("select distinct a from Auto a join fetch a.autoModels")
                .list();
        session.getTransaction().commit();
        session.close();

        for (Auto auto : result) {
            for (AutoModel model : auto.getAutoModels()) {
                System.out.println(model);
            }
        }
    }
}
