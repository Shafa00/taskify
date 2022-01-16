package com.taskify.model.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRqModel {
    @ApiModelProperty(name = "title", dataType = "string")
    @NotBlank
    @Size(max = 20)
    String title;

    @ApiModelProperty(name = "description", dataType = "string")
    @NotBlank
    @Size(max = 255)
    String description;

    @ApiModelProperty(name = "deadline", dataType = "string")
    @NotBlank
    @Size(max = 20)
    String deadline;

    @ApiModelProperty(name = "userIds", dataType = "List")
    @Size(max = 50)
    List<String> userIds;
}
