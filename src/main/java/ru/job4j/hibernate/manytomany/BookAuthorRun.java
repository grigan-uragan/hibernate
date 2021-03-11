package ru.job4j.hibernate.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookAuthorRun {
    private static final Logger LOG = LoggerFactory.getLogger(BookAuthorRun.class);

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            SessionFactory factory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            Session session = factory.openSession();
            session.beginTransaction();
            Book first = Book.of("Head First");
            Book second = Book.of("Java for beginners");
            Book three = Book.of("Lear Java with Uncle Bob");
            Book four = Book.of("Clean Code");
            Author bob = Author.of("Robert Martin");
            Author kathy = Author.of("Kathy Sierra");
            bob.addBooks(first);
            bob.addBooks(three);
            bob.addBooks(four);
            kathy.addBooks(first);
            kathy.addBooks(second);
            session.persist(bob);
            session.persist(kathy);
            Author author = session.get(Author.class, 1);
            session.remove(author);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("hibernate exception", e);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }
}
