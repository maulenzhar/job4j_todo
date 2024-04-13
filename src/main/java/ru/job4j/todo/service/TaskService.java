package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Task save(Task task);

    boolean update(Task task);

    Collection<Task> findAll();

    Collection<Task> findAllDone();

    Collection<Task> findAllNew();

    boolean deleteById(int id);

    Task makeTaskDone(int id);

    Optional<Task> getTaskById(int id);
}
