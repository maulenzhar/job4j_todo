package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.priority.PriorityService;
import ru.job4j.todo.service.task.TaskService;
import ru.job4j.todo.service.user.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private UserService userService;
    private PriorityService priorityService;

    public TaskController(TaskService taskService, UserService userService, PriorityService priorityService) {
        this.taskService = taskService;
        this.userService = userService;
        this.priorityService = priorityService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index";
    }

    @GetMapping("/done")
    public String getDone(Model model) {
        model.addAttribute("tasks", taskService.findAllDone());
        return "index";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findAllNew());
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, @SessionAttribute("user") User user) {
        task.setUser(user);
        taskService.save(task);
        return "redirect:/tasks";
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
        var isUpdated = taskService.makeTaskDone(id);
        if (!isUpdated) {
            model.addAttribute("message", "Что то пошло не так");
            return "error";
        }
        model.addAttribute("task", isUpdated);
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
