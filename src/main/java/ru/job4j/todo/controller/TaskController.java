package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }

    @GetMapping("/done")
    public String getDone(Model model) {
        model.addAttribute("tasks", taskService.findAll().stream()
                .filter(e -> e.isDone())
                .collect(Collectors.toList()));
        return "index";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findAll().stream()
                .filter(e -> !e.isDone())
                .collect(Collectors.toList()));
        return "index";
    }

    @GetMapping("/create")
    public String create() {
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            taskService.save(task);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "error";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/done/{id}")
    public String doneTask(Model model, @PathVariable int id) {
        var taskOptional = taskService.getTaskById(id);
        if (!taskOptional.isPresent()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors";
        }
        Task taskDone = taskService.getTaskById(taskOptional.get().getId()).get();
        taskDone.setDone(true);
        taskService.update(taskDone);
        model.addAttribute("task", taskDone);
        return "one-description";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.getTaskById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors";
        }
        model.addAttribute("task", taskOptional.get());
        return "one-description";
    }

    @GetMapping("/update/{id}")
    public String updateById(Model model, @PathVariable int id) {
        var taskOptional = taskService.getTaskById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors";
        }
        model.addAttribute("task", taskOptional.get());
        return "one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        boolean isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Кандидат с указанным идентификатором не найдена");
            return "error";
        }
        return "redirect:/tasks";

    }
}
