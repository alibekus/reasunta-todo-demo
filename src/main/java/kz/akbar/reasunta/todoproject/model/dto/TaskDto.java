package kz.akbar.reasunta.todoproject.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskDto {
    private int id;
    @NotBlank
    @Size(min = 2, max = 50, message = "title must be between 2 and 50 letters")
    private String title;
    @Size(max = 1000, message = "description must be less 1000 letters")
    private String description;
    private LocalDateTime activityDateTime;
    private LocalDateTime modified;
}
