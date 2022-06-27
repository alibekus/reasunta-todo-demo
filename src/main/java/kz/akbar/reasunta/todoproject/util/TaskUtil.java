package kz.akbar.reasunta.todoproject.util;

import kz.akbar.reasunta.todoproject.model.dto.TaskDto;
import kz.akbar.reasunta.todoproject.model.entity.Task;

import java.time.format.DateTimeFormatter;

public class TaskUtil {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static Task convert(TaskDto dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setActivityDateTime(dto.getActivityDateTime());
        return task;
    }

}
