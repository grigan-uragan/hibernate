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
        Candidate candidate = Candidate.of("Mike", "jun", 110.00);
        VacancyBase base = VacancyBase.of("superJob.ru", true);
        Vacancy javaDev = Vacancy.of("QA engineer", 70.00);
        Vacancy jsDev = Vacancy.of("Ruby developer", 20.00);
        base.addVacancy(javaDev);
        base.addVacancy(jsDev);
        candidate.setBase(base);
        create(factory, candidate);
        List<Candidate> candidates = allCandidates(factory);
        candidates.forEach(System.out::println);
        Candidate byId = findById(factory, 2);
        System.out.println(byId);
        List<Candidate> robert = findByName(factory, "Robert");
        robert.forEach(System.out::println);
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
        return tx(session -> session.createQuery("select distinct c from Candidate c"
                + " join fetch c.base vb join fetch vb.vacancies v").list(), factory);
    }

    public static Candidate findById(SessionFactory factory, int id) {
        return tx(session -> session.createQuery(
                "select distinct c from Candidate c join fetch c.base vb "
                        + "join fetch vb.vacancies v where c.id = :id", Candidate.class
        ).setParameter("id", id).uniqueResult(), factory);
    }

    public static List<Candidate> findByName(SessionFactory factory, String name) {
        return tx(session -> session
                .createQuery("select distinct c from Candidate c join fetch"
                        + " c.base vb join fetch vb.vacancies v where c.name = :name")
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
