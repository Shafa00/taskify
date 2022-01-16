package com.taskify.model.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeStatusRqModel {
    @ApiModelProperty(name = "taskId", dataType = "string")
    @NotBlank
    @Size(max = 50)
    String taskId;

    @ApiModelProperty(name = "userIds", dataType = "string")
    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "TODO|IN_PROGRESS|DONE", message = "Invalid status")
    String status;

}
