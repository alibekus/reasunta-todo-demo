package kz.akbar.reasunta.todoproject.service.impl;

import kz.akbar.reasunta.todoproject.exception.ErrorType;
import kz.akbar.reasunta.todoproject.exception.TaskException;
import kz.akbar.reasunta.todoproject.model.dto.TaskDto;
import kz.akbar.reasunta.todoproject.model.entity.Task;
import kz.akbar.reasunta.todoproject.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static kz.akbar.reasunta.todoproject.util.TaskUtil.DATE_TIME_FORMATTER;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TaskServiceImplTest {

    @Autowired
    private TaskService service;
    private Task task;
    private List<LocalDate> expected;
    private static final int ID = 10003;
    private static final String TITLE = "Sport";
    private static final String DESCRIPTION = "Go to the gym";
    private static final LocalDateTime ACTIVITY_DATETIME
            = LocalDateTime
            .parse(LocalDateTime
                    .of(2022,6,29,19,30)
                    .format(DATE_TIME_FORMATTER));
    private static final LocalDateTime CREATED
            = LocalDateTime
            .parse(LocalDateTime
                    .of(2022,6,24,16,24)
                    .format(DATE_TIME_FORMATTER));
    private static final LocalDateTime MODIFIED
            = LocalDateTime
            .parse(LocalDateTime
                    .of(2022,6,23,10,33)
                    .format(DATE_TIME_FORMATTER));
    private static final int SIZE = 6;
    private static final LocalDate BEGIN_DATE = LocalDate.of(2022,6,29);
    private static final LocalDate END_DATE = LocalDate.of(2022,6,30);

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(ID);
        task.setTitle(TITLE);
        task.setDescription(DESCRIPTION);
        task.setActivityDateTime(ACTIVITY_DATETIME);
        task.setCreated(CREATED);
        task.setModified(MODIFIED);
    }

    @Test
    void getAll() {
        assertEquals(SIZE, service.getAll().size());
    }

    @Test
    void getById() {
        assertEquals(ID, service.getById(ID).getId());
        assertEquals(TITLE, service.getById(ID).getTitle());
    }

    @Test
    void getByDate() {
        List<Task> byDate = service.getByDate(ACTIVITY_DATETIME.toLocalDate());
        byDate.stream().forEach(task -> {
            assertEquals(ACTIVITY_DATETIME.toLocalDate(), task.getActivityDateTime().toLocalDate());
        });
    }

    @Test
    void getBetweenDates() {
        expected = Lists.newArrayList(BEGIN_DATE, END_DATE);
        List<Task> betweenDates = service.getBetweenDates(BEGIN_DATE, END_DATE);
        List<LocalDate> actual = betweenDates
                .stream()
                .map(task -> task.getActivityDateTime().toLocalDate())
                .sorted()
                .toList();
        betweenDates.forEach(task -> {
            assertIterableEquals(expected, actual);
        });
    }

    @Test
    void getOverdue() {
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        List<Task> overdue = service.getOverdue();
        overdue.forEach(task -> {
            assertTrue(task.getActivityDateTime().isBefore(now));
        });
    }

    @Test
    void getByTitle() {
        List<Task> byTitle = service.getByTitle(TITLE);
        byTitle.forEach(task -> assertTrue(task.getTitle().equals(TITLE)));
    }

    @Test
    void getWithoutActivityDate() {
        List<Task> withoutActivityDate = service.getWithoutActivityDate();
        withoutActivityDate.forEach(task -> {
            assertNull(task.getActivityDateTime());
        });
    }

    @Test
    void create() {
        Task newTask = service.create(task);
        assertEquals(TITLE, newTask.getTitle());
        assertEquals(DESCRIPTION, newTask.getDescription());
    }

    @Test
    void update() {
        final String title = "Gymnastic";
        LocalDateTime activityDateTime
                = LocalDateTime
                .parse(LocalDateTime
                        .of(2022, 7, 1, 20, 30)
                        .format(DATE_TIME_FORMATTER));
        task.setTitle(title);
        TaskDto update = TaskDto.builder()
                .id(ID)
                .title(title)
                .description(DESCRIPTION)
                .activityDateTime(activityDateTime)
                .build();
        Task updated = service.update(update);
        assertTrue(updated.getTitle().equals(title));
        assertEquals(activityDateTime, updated.getActivityDateTime());
    }

    @Test
    void update_exception() {
        final String title = "Gymnastic";
        LocalDateTime activityDateTime
                = LocalDateTime
                .parse(LocalDateTime
                        .of(2022, 7, 1, 20, 30)
                        .format(DATE_TIME_FORMATTER));
        task.setTitle(title);
        TaskDto update = TaskDto.builder()
                .id(0)
                .title(title)
                .description(DESCRIPTION)
                .activityDateTime(activityDateTime)
                .build();
        TaskException taskException = assertThrows(TaskException.class, () -> service.update(update));
        String actual = taskException.getErrorType().getErrorCode();
        String expected = ErrorType.TASK_NOT_FOUND.getErrorCode();
        assertEquals(expected, actual);
    }

    @Test
    void delete() {
        int before = service.getAll().size();
        service.delete(ID);
        int after = service.getAll().size();
        assertEquals(before - 1, after);
    }

    @Test
    void delete_exception() {
        TaskException taskException = assertThrows(TaskException.class, () -> service.delete(0));
        String actual = taskException.getErrorType().getErrorCode();
        String expected = ErrorType.TASK_NOT_FOUND.getErrorCode();
        assertEquals(expected, actual);
    }
}