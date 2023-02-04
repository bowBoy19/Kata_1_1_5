package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;


public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE if not exists users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45),lastname VARCHAR(45),age TINYINT)")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {

            System.out.println("Таблица существует");
        }

    }


    @Override
    public void dropUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE if exists users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {

            System.out.println("Таблица удалена");

        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }


    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User").getResultList();
            for (User el : users) {
                System.out.println(el);
            }
            transaction.commit();

        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete  User").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {

        }
    }
}