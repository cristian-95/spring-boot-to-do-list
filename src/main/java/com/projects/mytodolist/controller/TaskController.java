package com.projects.mytodolist.controller;

import com.projects.mytodolist.model.Task;
import com.projects.mytodolist.repository.TaskRepository;
import com.projects.mytodolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = service.getAll();
        return tasks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") String id) {
        Task task = service.getById(id);
        return task == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = service.create(task);
        URI uri = service.getURIFromThisRequest(createdTask.getId());
        return ResponseEntity.created(uri).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") String id, @RequestBody Task update) {
        Task updated = service.update(UUID.fromString(id), update);
        return updated == null ? ResponseEntity.status(400).build() : ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable("id") String id, @RequestBody Boolean completed) {
        Task updated = service.updateStatus(UUID.fromString(id), completed);
        return updated == null ? ResponseEntity.status(400).build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") String id) {
        Task deleted = service.delete(UUID.fromString(id));
        return deleted == null ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
    }
}