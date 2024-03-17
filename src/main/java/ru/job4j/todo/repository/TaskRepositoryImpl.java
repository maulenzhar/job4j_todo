package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.config.DatasourceConfiguration;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository{
    private final DatasourceConfiguration datasourceConfiguration;

    public TaskRepositoryImpl(DatasourceConfiguration datasourceConfiguration) {
        this.datasourceConfiguration = datasourceConfiguration;
    }

    @Override
    public Task save(Task task) {
        Session session = datasourceConfiguration.sf().openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        boolean result = false;
        if (getTaskById(task.getId()).isPresent()) {
            Session session = datasourceConfiguration.sf().openSession();
            try {
                session.beginTransaction();
                session.update(task);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            } finally {
                session.close();
            }
            result = true;
        }
        return result;
    }

    @Override
    public Collection<Task> findAll() {
        Session session = datasourceConfiguration.sf().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Task order by id");
            session.getTransaction().commit();
            return  query.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        return List.of();
    }

    @Override
    public boolean deleteById(int id) {
        Session session = datasourceConfiguration.sf().openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            Query query = session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", id);
            result = query.executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        return result;
    }

    @Override
    public Optional<Task> getTaskById(int id) {
        Session session = datasourceConfiguration.sf().openSession();
        Optional<Task> result = Optional.empty();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                    "from Task as i where i.id = :fId", Task.class);
            query.setParameter("fId", id);
            session.getTransaction().commit();
            result = query.uniqueResultOptional();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
