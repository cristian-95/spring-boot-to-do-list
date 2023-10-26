package com.projects.mytodolist.service;

import com.projects.mytodolist.model.Task;
import com.projects.mytodolist.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public URI getURIFromThisRequest(UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    public Task getById(String id) {
        UUID uuid = UUID.fromString(id);
        if (repository.findById(uuid).isPresent()) {
            return repository.findById(uuid).get();
        }
        return null;
    }

    public Task create(Task task) {
        System.out.println(task);
        task.setCreatedDate(LocalDateTime.now());
        repository.save(task);
        return task;
    }

    public Task update(UUID id, Task update) {
        Task task = getTaskById(id);
        update.setCreatedDate(task.getCreatedDate());
        BeanUtils.copyProperties(update, task, "id");
        repository.save(task);
        return task;
    }

    private Task getTaskById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com ID: " + id));
    }

    public Task updateStatus(UUID id, Boolean completed) {
        Task task = getTaskById(id);
        task.setCompleted(completed);
        repository.save(task);
        return task;
    }

    public Task delete(UUID id) {
        if (repository.findById(id).isPresent()) {
            Task task = repository.findById(id).get();
            repository.deleteById(id);
            return task;
        }
        return null;
    }
}
