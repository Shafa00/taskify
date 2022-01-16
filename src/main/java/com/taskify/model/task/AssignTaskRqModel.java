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
public class AssignTaskRqModel {
    @ApiModelProperty(name = "taskId", dataType = "string")
    @NotBlank
    @Size(max = 50)
    String taskId;

    @ApiModelProperty(name = "userIds", dataType = "string")
    @NotBlank
    @Size(max = 50)
    List<String> userIds;

}
