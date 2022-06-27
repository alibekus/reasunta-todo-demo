package kz.akbar.reasunta.todoproject.service.impl;

import kz.akbar.reasunta.todoproject.exception.ErrorType;
import kz.akbar.reasunta.todoproject.exception.TaskException;
import kz.akbar.reasunta.todoproject.model.dto.TaskDto;
import kz.akbar.reasunta.todoproject.model.entity.Task;
import kz.akbar.reasunta.todoproject.repository.TaskRepository;
import kz.akbar.reasunta.todoproject.service.TaskService;
import kz.akbar.reasunta.todoproject.util.TaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kz.akbar.reasunta.todoproject.util.TaskUtil.DATE_TIME_FORMATTER;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAll() {
        return repository.getAll();
    }

    public Task getById(int id) throws TaskException {
        Optional<Task> one = repository.getOne(id);
        if (one.isPresent()) {
           return one.get();
        } else {
            throw new TaskException(ErrorType.TASK_NOT_FOUND);
        }
    }

    public List<Task> getByDate(LocalDate date) {
        return repository.getByDate(date);
    }

    public List<Task> getBetweenDates(LocalDate begin, LocalDate end) {

        return repository.getAllBetweenDates(begin, end);
    }

    public List<Task> getOverdue() {
        return repository.getOverdue();
    }

    public List<Task> getByTitle(String title) {
        return repository.getByTitle(title);
    }

    @Override
    public List<Task> getWithoutActivityDate() {
        return repository.getWithoutActivityDate();
    }

    public Task create(Task task) {
        Assert.notNull(task, "task must not be null");
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        task.setCreated(now);
        task.setModified(now);
        return repository.save(task);
    }

    public Task update(TaskDto dto) throws TaskException {
        Assert.notNull(dto, "task must not be null");
        Optional<Task> one = repository.getOne(dto.getId());
        Task task = TaskUtil.convert(dto);
        if (one.isPresent()) {
            task.setCreated(one.get().getCreated());
        } else {
            throw new TaskException(ErrorType.TASK_NOT_FOUND);
        }
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        task.setModified(now);
        log.info("UPDATE BEFORE: {}", task);
        Task updated = repository.save(task);
        log.info("UPDATE AFTER: {}", updated);
        return updated;
    }


    public void delete(int id) throws TaskException{
        Optional<Task> one = repository.getOne(id);
        if (one.isPresent()) {
            repository.delete(getById(id));
        } else {
            throw new TaskException(ErrorType.TASK_NOT_FOUND);
        }
    }

}
