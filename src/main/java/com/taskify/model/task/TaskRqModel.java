package com.taskify.model.task;

import com.sun.istack.NotNull;
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
public class TaskRqModel {
    @NotNull
    String title;
    @NotNull
    String description;
    String deadline;
    @NotNull
    String status;
    @NotNull
    List<String> userIds;
}
