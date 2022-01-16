package com.taskify.model.organization;

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
public class SignupRsModel {
    @ApiModelProperty(name = "userId", dataType = "string")
    String userId;

    @ApiModelProperty(name = "username", dataType = "string")
    String username;

    @ApiModelProperty(name = "name", dataType = "string")
    String name;

    @ApiModelProperty(name = "surname", dataType = "string")
    String surname;

    @ApiModelProperty(name = "email", dataType = "string")
    String email;

    @ApiModelProperty(name = "organizationId", dataType = "string")
    String organizationId;

    @ApiModelProperty(name = "organizationName", dataType = "string")
    String organizationName;

    @ApiModelProperty(name = "organizationPhoneNumber", dataType = "string")
    String organizationPhoneNumber;

    @ApiModelProperty(name = "organizationAddress", dataType = "string")
    String organizationAddress;
}
