package kz.akbar.reasunta.todoproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.akbar.reasunta.todoproject.exception.ErrorType;
import kz.akbar.reasunta.todoproject.exception.TaskException;
import kz.akbar.reasunta.todoproject.model.dto.TaskDto;
import kz.akbar.reasunta.todoproject.model.entity.Task;
import kz.akbar.reasunta.todoproject.service.impl.TaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static kz.akbar.reasunta.todoproject.util.TaskUtil.convert;

@Slf4j
@RestController
@RequestMapping(
        value = "/task",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "To Do planner", description = "Schedule your tasks")
public class TaskController {

    private final TaskServiceImpl service;

    public TaskController(TaskServiceImpl service) {
        this.service = service;
    }

    @GetMapping(
            value = "/all"
    )
    @Operation(
            summary = "Get all tasks",
            description = "Requests task entities"
    )
    public ResponseEntity<List<Task>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping(
            value="/{id}"
    )
    @Operation(
            summary = "Get task by id",
            description = "Request task by its id for update for example"
    )
    public ResponseEntity getById(@PathVariable int id) {
        try {
            Task task = service.getById(id);
            return ResponseEntity.ok(task);
        } catch (TaskException exp) {
            return new ResponseEntity<>(exp.getErrorType().getErrorCode(), exp.getErrorType().getStatus());
        }
    }

    @GetMapping(
            value = "/by-date"
    )
    @Operation(
            summary = "Get task for some date",
            description = "Task in the date from 00:00:00 to 23:59:59"
    )
    public ResponseEntity<List<Task>> getByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        log.info("GET BY DATE: {}", date);
        return ResponseEntity.ok(service.getByDate(date));
    }

    @GetMapping(
            value = "/between-dates"
    )
    @Operation(
            summary = "Get tasks between requested dates",
            description = "Request tasks from yyyy-MM-dd 00:00:00 to yyyy-MM-dd 23:59:59"
    )
    public ResponseEntity<List<Task>> getTasksBetweenDates(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate beginDate,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {
        return ResponseEntity.ok(service.getBetweenDates(beginDate, endDate));
    }

    @GetMapping(
            value = "/overdue"
    )
    @Operation(
            summary = "Get overdue tasks",
            description = "Request tasks which dates before now date-time"
    )
    public ResponseEntity<List<Task>> getOverdueTasks() {
        return ResponseEntity.ok(service.getOverdue());
    }

    @GetMapping(
            value = "/by-title"
    )
    @Operation(
            summary = "Get tasks by its title",
            description = "Request tasks which titles exactly correspond in the request"
    )
    public ResponseEntity<List<Task>> getTasksByTitle(@RequestParam String title) {
        return ResponseEntity.ok(service.getByTitle(title));
    }

    @GetMapping(
            value = "/without-date"
    )
    @Operation(
            summary = "Get tasks without activity date-time",
            description = "Request tasks with null activity date-time"
    )
    public ResponseEntity<List<Task>> getWithoutActivityDate() {
        return ResponseEntity.ok(service.getWithoutActivityDate());
    }

    @PostMapping(
            value = ""
    )
    @Operation(
            summary = "Create new task",
            description = "Return new created"
    )
    public ResponseEntity<Task> createNew(@RequestBody TaskDto dto) {
        log.info("NEW TASK: {}", dto);
        return ResponseEntity.ok(service.create(convert(dto)));
    }

    @PutMapping(
            value = ""
    )
    @Operation(
            summary = "Update task",
            description = "Update task by its id"
    )
    public ResponseEntity updateTask(@RequestBody TaskDto dto) {
        log.info("UPDATE TASK: {}", dto);
        try {
            return ResponseEntity.ok(service.update(dto));
        } catch (TaskException e) {
            return new ResponseEntity<>(e.getErrorType(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @Operation(
            summary = "Delete task",
            description = "Delete task by its id"
    )
    public ResponseEntity<?> deleteTask(@PathVariable int id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (TaskException e) {
            return new ResponseEntity(e.getErrorType(), HttpStatus.NOT_FOUND);
        }
    }
}
