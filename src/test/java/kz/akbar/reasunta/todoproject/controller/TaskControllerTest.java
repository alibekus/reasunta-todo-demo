package kz.akbar.reasunta.todoproject.controller;

import kz.akbar.reasunta.todoproject.model.dto.TaskDto;
import kz.akbar.reasunta.todoproject.model.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kz.akbar.reasunta.todoproject.util.TaskUtil.DATE_TIME_FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TaskControllerTest {

    @Autowired
    private TaskController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static final int SIZE = 6;
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

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAll() {
        List<Task> tasks = restTemplate
                .getForEntity("http://localhost:" + port + "/task/all", ArrayList.class)
                .getBody();
        assertThat(tasks.size()).isEqualTo(SIZE);
    }

    @Test
    void getById() {
        Task task = restTemplate
                .getForEntity("http://localhost:" + port + "/task/" + ID, Task.class)
                .getBody();
        assertThat(task.getId()).isEqualTo(ID);
    }

    @Test
    void getByDate() {
        LocalDate chosenDate = LocalDate.of(2022, 6, 29);
        List<Task> tasks = Lists.newArrayList(restTemplate
                .getForObject("http://localhost:" + port + "/task/by-date?date=2022-06-29", Task[].class));
        tasks.forEach(task ->
            assertEquals(chosenDate, task.getActivityDateTime().toLocalDate()));
    }

    @Test
    void getTasksBetweenDates() {
        LocalDate beginDate = LocalDate.of(2022,6,29);
        LocalDate endDate = LocalDate.of(2022,6,30);
        List<LocalDate> expected = Lists.newArrayList(beginDate, endDate);
        List<Task> betweenTasks = Lists.newArrayList(restTemplate.getForObject("http://localhost:" + port
                        + "/task/between-dates?beginDate=2022-06-29&endDate=2022-06-30", Task[].class));
        List<LocalDate> actual = betweenTasks.stream().map(task
                -> task.getActivityDateTime().toLocalDate()).sorted().toList();
        assertIterableEquals(expected, actual);
    }

    @Test
    void getOverdueTasks() {
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        List<Task> overdueTasks = Lists.newArrayList(restTemplate.getForObject("http://localhost:" + port
                        + "/task/overdue", Task[].class));
        overdueTasks.forEach(task -> assertTrue(task.getActivityDateTime().isBefore(now)));
    }

    @Test
    void getTasksByTitle() {
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        List<Task> overdueTasks = Lists.newArrayList(restTemplate.getForObject("http://localhost:" + port
                        + "/task/overdue", Task[].class));
        overdueTasks.forEach(task -> assertTrue(task.getActivityDateTime().isBefore(now)));
    }

    @Test
    void getWithoutActivityDate() {
        List<Task> withoutDate = Lists.newArrayList(restTemplate.getForObject("http://localhost:" + port
                        + "/task/without-date", Task[].class));
        withoutDate.forEach(task -> assertNull(task.getActivityDateTime()));
    }

    @Test
    void createNew() {
        TaskDto taskDto = TaskDto.builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .activityDateTime(ACTIVITY_DATETIME)
                .build();
        Task saved = restTemplate.postForEntity("http://localhost:" + port
                        + "/task", taskDto, Task.class)
                .getBody();
        assertEquals(TITLE, saved.getTitle());
        assertEquals(DESCRIPTION, saved.getDescription());
        assertEquals(ACTIVITY_DATETIME, saved.getActivityDateTime());
    }

    @Test
    void updateTask() {
        final String updateTitle = "Gymnastic" ;
        final LocalDateTime updateActivityDate = LocalDateTime.of(2022,7,1,20,30);
        TaskDto updateTask = TaskDto.builder()
                .id(ID)
                .title(updateTitle)
                .description(DESCRIPTION)
                .activityDateTime(updateActivityDate)
                .build();
        restTemplate.put("http://localhost:" + port + "/task", updateTask);
        Task updatedTask = restTemplate
                .getForEntity("http://localhost:" + port + "/task/" + ID, Task.class)
                .getBody();
        assertEquals(updateTitle, updatedTask.getTitle());
        assertEquals(updateActivityDate, updatedTask.getActivityDateTime());
    }

    @Test
    void deleteTask() {
        restTemplate.delete("http://localhost:" + port + "/task/" + ID);
        List<Task> tasks = restTemplate
                .getForEntity("http://localhost:" + port + "/task/all", ArrayList.class)
                .getBody();
        assertThat(tasks.size()).isEqualTo(SIZE - 1);
    }
}