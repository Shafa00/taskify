package com.taskify.model.task;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRsModel {
    String taskId;
    String title;
    String description;
    String deadline;
    String status;
    List<String> userIds;
    String organizationId;
}
