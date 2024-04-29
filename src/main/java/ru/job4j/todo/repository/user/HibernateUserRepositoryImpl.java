/*
package ru.job4j.todo.repository.user;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.config.DatasourceConfiguration;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Slf4j
@Repository
public class HibernateUserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    public HibernateUserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<User> save(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Internal error: {}", e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from User as u where u.password = :fPassword and u.login = :fLogin");
            query.setParameter("fLogin", login);
            query.setParameter("fPassword", password);
            session.getTransaction().commit();
            return  Optional.of((User) query.uniqueResult());
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.empty();
    }
}
*/
