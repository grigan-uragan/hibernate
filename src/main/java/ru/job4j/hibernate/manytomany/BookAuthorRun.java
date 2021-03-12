package ru.job4j.hibernate.manytomany;

import org.hibernate.Session;
import ru.job4j.hibernate.util.HibernateUtil;

public class BookAuthorRun {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
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
        Author author = session.get(Author.class, 2);
        session.remove(author);
        session.getTransaction().commit();
        session.close();
    }
}
