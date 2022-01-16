package com.taskify.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRsModel {
    @ApiModelProperty(name = "userId", dataType = "string")
    String userId;

    @ApiModelProperty(name = "name", dataType = "string")
    String name;

    @ApiModelProperty(name = "surname", dataType = "string")
    String surname;

    @ApiModelProperty(name = "email", dataType = "string")
    String email;

    @ApiModelProperty(name = "organizationId", dataType = "string")
    String organizationId;
}
