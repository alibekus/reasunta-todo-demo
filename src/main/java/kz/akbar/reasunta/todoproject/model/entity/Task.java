package kz.akbar.reasunta.todoproject.model.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@ToString
@Entity
@Table(name = "t_task")
@EqualsAndHashCode(callSuper=true)
public class Task extends Activity {
}
