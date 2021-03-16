package ru.job4j.hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.hibernate.util.HibernateUtil;

import java.util.List;
import java.util.function.Function;

public class HiberRun {
    public static final Logger LOG = LoggerFactory.getLogger(HiberRun.class);

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Candidate candidate = Candidate.of("Antony", "exp", 11.00);
        create(factory, candidate);
        List<Candidate> list = allCandidates(factory);
        Candidate newCandidate = Candidate.of("Mark", "exp", 90.00);
        list.forEach(System.out::println);
        update(factory, newCandidate, 4);
        Candidate byId = findById(factory, 4);
        System.out.println(byId);
        List<Candidate> byName = findByName(factory, "Tom");
        byName.forEach(System.out::println);
        delete(factory, 1);
        List<Candidate> newList = allCandidates(factory);
        newList.forEach(System.out::println);
    }

    public static void delete(SessionFactory factory, int id) {
        tx(session -> session.createQuery("delete from Candidate c where c.id = :id")
                .setParameter("id", id).executeUpdate(), factory);
    }

    public static void update(SessionFactory factory, Candidate candidate, int id) {
        tx(session -> session
                .createQuery("update Candidate c set c.name = :name, c.salary = :salary "
                        + "where c.id = :id")
                .setParameter("name", candidate.getName())
                .setParameter("salary", candidate.getSalary())
                .setParameter("id", id)
                .executeUpdate(), factory);
    }

    public static void create(SessionFactory factory, Candidate candidate) {
        tx(session -> session.save(candidate), factory);
    }

    public static List<Candidate> allCandidates(SessionFactory factory) {
        return tx(session -> session.createQuery("from Candidate").list(), factory);
    }

    public static Candidate findById(SessionFactory factory, int id) {
        return tx(session -> session.get(Candidate.class, id), factory);
    }

    public static List<Candidate> findByName(SessionFactory factory, String name) {
        return tx(session -> session
                .createQuery("from Candidate c where c.name = :name")
                .setParameter("name", name).list(), factory);
    }

    public static <T> T tx(Function<Session, T> command, SessionFactory factory) {
        try {
            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            T result = command.apply(session);
            transaction.commit();
            session.close();
            return result;
        } catch (Exception e) {
            LOG.error("transaction failed", e);
            factory.getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }

}
