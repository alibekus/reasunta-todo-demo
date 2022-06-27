package kz.akbar.reasunta.todoproject.repository;

import kz.akbar.reasunta.todoproject.model.entity.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> getAll();
    Optional<Task> getOne(int id);
    List<Task> getByDate(LocalDate date);

    List<Task> getAllBetweenDates(LocalDate beginDate, LocalDate endDate);

    List<Task> getOverdue();

    List<Task> getByTitle(String title);

    List<Task> getWithoutActivityDate();

    Task save(Task task);

    void delete(Task task);
}
