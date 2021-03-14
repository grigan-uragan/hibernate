package ru.job4j.hibernate.manytomany.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.hibernate.manytomany.model.City;
import ru.job4j.hibernate.manytomany.model.Human;
import ru.job4j.hibernate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class HbmStore implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(HbmStore.class);
    private SessionFactory sf;

    private HbmStore() {
        sf = HibernateUtil.getSessionFactory();
    }

    public void addNewHuman(Human human, String[] ids) {
       try (Session session = sf.openSession()) {
           session.beginTransaction();
           for (String id : ids) {
               City city = session.find(City.class, Integer.parseInt(id));
               human.addCity(city);
           }
           session.save(human);
           session.getTransaction().commit();
           session.close();
       } catch (Exception e) {
           LOG.error("some trouble with addNewCity");
            sf.getCurrentSession().getTransaction().rollback();
       }
    }

    public List<City> allCity() {
        List<City> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.createQuery("select c from City c", City.class).list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("some trouble with get allCity", e);
            sf.getCurrentSession().getTransaction().rollback();
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        sf.close();
    }

    public static HbmStore instOf() {
        return Lazy.INST;
    }

    public static final class Lazy {
        public static final HbmStore INST = new HbmStore();
    }
}
