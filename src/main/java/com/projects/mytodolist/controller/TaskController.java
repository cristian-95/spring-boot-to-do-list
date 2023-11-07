package com.projects.mytodolist.controller;

import com.projects.mytodolist.model.Task;
import com.projects.mytodolist.repository.TaskRepository;
import com.projects.mytodolist.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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

    @Operation(summary = "Retorna todas as tarefas.")
    @ApiResponse(responseCode = "200", description = "Operação bem sucedida.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))})
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = service.getAll();
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Encontra uma tarefa especifica.")
    @ApiResponse(responseCode = "200", description = "Operação bem sucedida.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))})
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))})
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Registra uma nova tarefa.")
    @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))})
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid Task task) {
        Task createdTask = service.create(task);
        URI uri = service.getURIFromThisRequest(createdTask.getId());
        return ResponseEntity.created(uri).body(createdTask);
    }

    @Operation(summary = "Modifica uma tarefa.")
    @ApiResponse(responseCode = "200", description = "Operação bem sucedida.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))})
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") String id, @RequestBody Task update) {
        Task updated = service.update(UUID.fromString(id), update);
        return updated == null ? ResponseEntity.status(400).build() : ResponseEntity.ok(updated);
    }

    @Operation(summary = "Modifica o status de uma tarefa.")
    @ApiResponse(responseCode = "200", description = "Operação bem sucedida.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))})
    @PutMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable("id") String id, @RequestBody Boolean completed) {
        Task updated = service.updateStatus(UUID.fromString(id), completed);
        return updated == null ? ResponseEntity.status(400).build() : ResponseEntity.ok(updated);
    }

    @Operation(summary = "Deleta uma tarefa.")
    @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") String id) {
        Task deleted = service.delete(UUID.fromString(id));
        return deleted == null ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
    }
}