package com.projects.mytodolist.service;

import com.projects.mytodolist.controller.TaskController;
import com.projects.mytodolist.exception.TaskNotFoundException;
import com.projects.mytodolist.model.Task;
import com.projects.mytodolist.model.TaskDTO;
import com.projects.mytodolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<Task> getAll() {
        List<Task> taskList = repository.findAll();
        return taskList.stream().map(t -> t.add(linkTo(methodOn(TaskController.class).getTaskByIdString(t.getId().toString())).withSelfRel())).toList();
    }

    public Task getById(String id) {
        UUID uuid = UUID.fromString(id);
        Task task = getById(uuid);
        task.add(linkTo(methodOn(TaskController.class).getTaskByIdString(task.getId().toString())).withSelfRel());
        task.add(linkTo(methodOn(TaskController.class).getAllTasks()).withRel("Listagem"));
        return task;
    }

    public Task create(TaskDTO dto) {
        Task task = new Task(dto);
        repository.save(task);
        task.add(linkTo(methodOn(TaskController.class).getTaskById(task.getId())).withSelfRel());
        return task;
    }

    public Task update(String id, TaskDTO dto) {
        UUID uuid = UUID.fromString(id);
        Task task = getById(uuid);
        task.setTitle(dto.title() != null ? dto.title() : task.getTitle());
        task.setDescription(dto.description() != null ? dto.description() : task.getDescription());
        repository.save(task);
        task.add(linkTo(methodOn(TaskController.class).getTaskById(uuid)).withSelfRel());
        return task;
    }

    public Task getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada."));
    }

    public Task updateStatus(String id, Boolean completed) {
        UUID uuid = UUID.fromString(id);
        Task task = getById(uuid);
        task.setCompleted(completed);
        repository.save(task);
        task.add(linkTo(methodOn(TaskController.class).getTaskById(uuid)).withSelfRel());
        return task;
    }

    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        if (repository.findById(uuid).isPresent()) {
            Task task = repository.findById(uuid).get();
            repository.delete(task);
        } else throw new TaskNotFoundException("Tarefa não encontrada.");
    }
}
