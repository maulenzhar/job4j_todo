package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.task.TaskRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceSimple implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceSimple(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    public Collection<Task> findAllDone() {
        return taskRepository.findAll().stream()
                .filter(e -> e.isDone())
                .collect(Collectors.toList());
    }

    public Collection<Task> findAllNew() {
        return taskRepository.findAll().stream()
                .filter(e -> !e.isDone())
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(int id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public Task makeTaskDone(int id) {
        Task result = new Task();
        Optional<Task> taskDone = getTaskById(id);
        if (taskDone.isPresent()) {
            Task task = taskDone.get();
            task.setDone(true);
            update(task);
            result = task;
        }

        return result;
    }

    @Override
    public Optional<Task> getTaskById(int id) {
        return taskRepository.getTaskById(id);
    }
}
