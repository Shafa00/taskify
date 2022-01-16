package com.taskify.entity;

import com.taskify.utility.TaskStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "task_title")
    private String title;

    @Column(name = "task_description")
    private String description;

    @Column(name = "task_deadline")
    private LocalDateTime deadline;

    @Column(name = "task_status")
    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @ManyToMany
    @JoinTable(
            name = "rel_user_task",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;

    @ManyToOne
    @JoinTable(
            name = "rel_task_organization",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id", referencedColumnName = "id"))
    private Organization organization;
}
