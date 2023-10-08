package com.projects.mytodolist.controller;

import com.projects.mytodolist.model.Task;
import com.projects.mytodolist.repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
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

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        if (repository.existsById(uuid)) {
            return repository.findAllById(Collections.singleton(uuid)).get(0);
        }
        return null;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setCreatedDate(LocalDateTime.now());
        repository.save(task);
        return task;
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") String id, @RequestBody Task update) {
        UUID uuid = UUID.fromString(id);

        if (repository.findById(uuid).isPresent()) {
            Task task = repository.findById(uuid).get();
            update.setCreatedDate(task.getCreatedDate());
            BeanUtils.copyProperties(update, task, "id");
            repository.save(task);
            return task;
        }
        return null;
    }

    @PutMapping("/{id}/status")
    public Task updateStatus(@PathVariable("id") String id, @RequestBody Boolean completed) {
        UUID uuid = UUID.fromString(id);
        if (repository.findById(uuid).isPresent()) {

            Task task = repository.findById(uuid).get();
            task.setCompleted(completed);
            repository.save(task);
            return task;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public Task deleteTask(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        if (repository.findById(uuid).isPresent()) {
            Task task = repository.findById(uuid).get();
            repository.deleteById(uuid);
            return task;
        }
        return null;
    }
}
