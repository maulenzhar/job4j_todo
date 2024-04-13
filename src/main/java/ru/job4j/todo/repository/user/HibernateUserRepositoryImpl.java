package ru.job4j.todo.repository.user;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.config.DatasourceConfiguration;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
public class HibernateUserRepositoryImpl implements UserRepository {

    private final DatasourceConfiguration datasourceConfiguration;

    public HibernateUserRepositoryImpl(DatasourceConfiguration datasourceConfiguration) {
        this.datasourceConfiguration = datasourceConfiguration;
    }

    @Override
    public User save(User user) {
        Session session = datasourceConfiguration.sf().openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = datasourceConfiguration.sf().openSession();
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
