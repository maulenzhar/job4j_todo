package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepositoryCommandImpl implements TaskRepository {

    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean update(Task task) {
        return crudRepository.run(
                "UPDATE Task "
                        + "SET title       = :fTitle, "
                        + "    description = :fDescription, "
                        + "    created     = :fCreated, "
                        + "    done        = :fDone "
                        + "WHERE id = :fId",
                Map.of("fId", task.getId(),
                        "fTitle", task.getTitle(),
                        "fDescription", task.getDescription(),
                        "fCreated", task.getCreated(),
                        "fDone", task.isDone())
        );
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query("from Task order by id asc", Task.class);
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.run(
                "delete from Task where id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public Optional<Task> getTaskById(int id) {
        return crudRepository.optional(
                "from Task where id = :fId", Task.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Task> findAllNew() {
        return crudRepository.query("from Task where done is false order by id asc", Task.class);
    }

    @Override
    public Collection<Task> findAllDone() {
        return crudRepository.query("from Task where done is true order by id asc", Task.class);
    }

    @Override
    public boolean makeTaskDone(int id) {
        crudRepository.run(
                "UPDATE Task SET done = true WHERE id = :fId",
                Map.of("fId", id)
        );
        return false;
    }
}
