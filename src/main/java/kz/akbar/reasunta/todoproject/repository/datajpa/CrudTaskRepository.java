package kz.akbar.reasunta.todoproject.repository.datajpa;

import kz.akbar.reasunta.todoproject.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
public interface CrudTaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t WHERE t.activityDateTime >= :beginDayTime AND t.activityDateTime <= :endDayTime")
    List<Task> getTasksByActivityDate(
            @Param("beginDayTime") LocalDateTime beginDayTime,
            @Param("endDayTime") LocalDateTime endDayTime
    );

    @Query("SELECT t FROM Task t WHERE t.activityDateTime >= :begin AND t.activityDateTime <= :end")
    List<Task> getBetweenActivityDates(
            @Param("begin") LocalDateTime beginDateTime,
            @Param("end") LocalDateTime endDateTime
    );

    @Query("SELECT t FROM Task t WHERE t.activityDateTime < :currentDateTime")
    List<Task> getOverdue(@Param("currentDateTime") LocalDateTime currentDateTime);

    List<Task> getTaskByTitle(String title);

    @Query("SELECT t FROM Task t WHERE t.activityDateTime IS NULL")
    List<Task> getWithoutActivityDate();

}
