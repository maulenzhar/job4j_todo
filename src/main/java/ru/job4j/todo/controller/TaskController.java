package ru.job4j.todo.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.category.CategoryService;
import ru.job4j.todo.service.priority.PriorityService;
import ru.job4j.todo.service.task.TaskService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

@Controller
@Slf4j
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    private CategoryService categoryService;
    private PriorityService priorityService;

    public TaskController(TaskService taskService, CategoryService categoryService, PriorityService priorityService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
        this.priorityService = priorityService;
    }

    @GetMapping
    public String getAll(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Collection<Task> tasks = taskService.findAll();
        for (Task task : tasks) {
            log.info("Task: {}", task);
            task.setCreated(task.getCreated()
                    .withZoneSameInstant(ZoneId.of(user.getUserZone())));
        }
        model.addAttribute("tasks", tasks);
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
        model.addAttribute("categories", categoryService.findAll());
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, @RequestParam List<Integer> categoriesId, @SessionAttribute("user") User user) {

        List<Category> categories = categoryService.findByIds(categoriesId);

        task.setUser(user);
        task.setTaskCategories(categories);
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
