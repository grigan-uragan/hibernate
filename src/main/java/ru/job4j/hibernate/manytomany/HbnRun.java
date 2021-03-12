package ru.job4j.hibernate.manytomany;

import org.hibernate.Session;
import ru.job4j.hibernate.util.HibernateUtil;

public class HbnRun {

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Address one = Address.of("Tversckaya", "11");
        Address two = Address.of("Lenina", "14");
        Person nick = Person.of("Nikolay");
        nick.addAddress(one);
        nick.addAddress(two);
        Person bob = Person.of("Bob");
        bob.addAddress(one);
        session.persist(nick);
        session.persist(bob);
        Person person = session.get(Person.class, 2);
        session.remove(person);
        session.getTransaction().commit();
        session.close();
    }
}
