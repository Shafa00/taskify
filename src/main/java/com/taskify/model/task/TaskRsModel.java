package com.taskify.model.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRsModel {
    @ApiModelProperty(name = "taskId", dataType = "string")
    String taskId;

    @ApiModelProperty(name = "title", dataType = "string")
    String title;

    @ApiModelProperty(name = "description", dataType = "string")
    String description;

    @ApiModelProperty(name = "deadline", dataType = "localDateTime")
    LocalDateTime deadline;

    @ApiModelProperty(name = "status", dataType = "string")
    String status;

    @ApiModelProperty(name = "userIds", dataType = "List")
    List<String> userIds;

    @ApiModelProperty(name = "organizationId", dataType = "string")
    String organizationId;
}
