package ru.job4j.todo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskControllerTest {
    private TaskService taskService;

    private TaskController taskController;

    @BeforeEach
    public void initServices() {
        taskService = mock(TaskService.class);
        taskController = new TaskController(taskService);
    }

    @Test
    public void whenRequestGetAll() {
        var task1 = new Task("test1", LocalDateTime.now(), false);
        var task2 = new Task("test2", LocalDateTime.now(), false);

        var expectedVacancies = List.of(task1, task2);
        when(taskService.findAll()).thenReturn(expectedVacancies);

        var model = new ConcurrentModel();
        var view = taskController.getAll(model);
        var actualTasks = model.getAttribute("tasks");

        assertThat(view).isEqualTo("index");
        assertThat(actualTasks).isEqualTo(expectedVacancies);
    }

    @Test
    public void whenRequestGetById() {
        var expectedTask = new Task("test1", LocalDateTime.now(), false);

        var filmSessionArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(taskService.getTaskById(filmSessionArgumentCaptor.capture())).thenReturn(Optional.of(expectedTask));

        var model = new ConcurrentModel();
        var view = taskController.getById(model, expectedTask.getId());
        var filmSessionVacancy = filmSessionArgumentCaptor.getValue();

        assertThat(view).isEqualTo("one-description");
        assertThat(filmSessionVacancy).isEqualTo(expectedTask.getId());
    }
}