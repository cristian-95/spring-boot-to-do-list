package com.projects.mytodolist.controller;

import com.projects.mytodolist.model.Task;
import com.projects.mytodolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository repository;

    @GetMapping
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setId(UUID.randomUUID());
        task.setCreatedDate(LocalDateTime.now());
        repository.save(task);
        return task;
    }
}
