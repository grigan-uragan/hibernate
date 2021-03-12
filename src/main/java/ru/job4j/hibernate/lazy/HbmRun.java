package ru.job4j.hibernate.lazy;

import org.hibernate.Session;
import ru.job4j.hibernate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        List<Category> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        list = session.createQuery("select distinct c from Category c join fetch c.tasks").list();
        session.getTransaction().commit();
        session.close();

        for (Category category : list) {
            for (Task task: category.getTasks()) {
                System.out.println(task);
            }
        }
    }
}
