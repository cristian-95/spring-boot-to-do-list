package com.projects.mytodolist.service;

import com.projects.mytodolist.model.Task;
import com.projects.mytodolist.repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
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
        if (repository.existsById(uuid)) {
            return repository.findAllById(Collections.singleton(uuid)).get(0);
        }
        return null;
    }

    public Task create(Task task) {
        task.setCreatedDate(LocalDateTime.now());
        repository.save(task);
        return task;
    }

    public Task update(UUID id, Task update) {
        if (repository.findById(id).isPresent()) {
            Task task = repository.findById(id).get();
            update.setCreatedDate(task.getCreatedDate());
            BeanUtils.copyProperties(update, task, "id");
            repository.save(task);
            return task;
        }
        return null;
    }

    public Task updateStatus(UUID id, Boolean completed) {
        if (repository.findById(id).isPresent()) {
            Task task = repository.findById(id).get();
            task.setCompleted(completed);
            repository.save(task);
            return task;
        }
        return null;
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
