package kz.akbar.reasunta.todoproject.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static kz.akbar.reasunta.todoproject.util.TaskUtil.DATE_TIME_PATTERN;


@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
public abstract class Activity extends BaseEntity{

    @NotBlank
    @Size(min = 2, max = 50, message = "title must be between 2 and 50 letters")
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 1000, message = "description must be less 1000 letters")
    @Column(name = "description")
    private String description;

//    @NotNull(message = "activity date-time must have value")
    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    @Column(name = "activity_datetime")
    private LocalDateTime activityDateTime;

    @NotNull
//    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    @Column
    private LocalDateTime created;

    @NotNull
//    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    @Column
    private LocalDateTime modified;

}
