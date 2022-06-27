package kz.akbar.reasunta.todoproject.service;

import kz.akbar.reasunta.todoproject.model.dto.TaskDto;
import kz.akbar.reasunta.todoproject.model.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

     List<Task> getAll();

     Task getById(int id);

     List<Task> getByDate(LocalDate date);

     List<Task> getBetweenDates(LocalDate begin, LocalDate end);

     List<Task> getOverdue();

     List<Task> getByTitle(String title);

     List<Task> getWithoutActivityDate();

     Task create(Task task);

     Task update(TaskDto taskDto);

     void delete(int id);
}
