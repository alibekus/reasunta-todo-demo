package kz.akbar.reasunta.todoproject.repository.datajpa;

import kz.akbar.reasunta.todoproject.model.entity.Task;
import kz.akbar.reasunta.todoproject.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static kz.akbar.reasunta.todoproject.util.TaskUtil.DATE_TIME_FORMATTER;

@Slf4j
@Repository
public class DataJpaTaskRepository implements TaskRepository {

    private final CrudTaskRepository repository;

    public DataJpaTaskRepository(CrudTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Task> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Task> getOne(int id) {
        Optional<Task> byId = repository.findById(id);
        log.info("REPOSITORY GET BY ID: {}", byId);
        return byId;
    }

    @Override
    public List<Task> getByDate(LocalDate date) {
        LocalDateTime beginOfDay = LocalDateTime.of(date, LocalTime.of(0, 0,0));
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.of(23, 59,59));
        return repository.getTasksByActivityDate(beginOfDay, endOfDay);
    }

    @Override
    public List<Task> getAllBetweenDates(LocalDate beginDate, LocalDate endDate) {
        LocalDateTime beginDateTime = LocalDateTime.of(beginDate, LocalTime.of(0, 0));
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59));
        return repository.getBetweenActivityDates(beginDateTime, endDateTime);
    }

    @Override
    public List<Task> getOverdue() {
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        log.info("REPOSITORY OVERDUE NOW: {}", now);
        return repository.getOverdue(now);
    }

    @Override
    public List<Task> getByTitle(String title) {
        return repository.getTaskByTitle(title);
    }

    @Override
    public List<Task> getWithoutActivityDate() {
        return repository.getWithoutActivityDate();
    }

    @Override
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    public void delete(Task task) {
        repository.delete(task);
    }
}
