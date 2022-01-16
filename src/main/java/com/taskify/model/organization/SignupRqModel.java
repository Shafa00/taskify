package com.taskify.model.organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRqModel {
    @ApiModelProperty(name = "username", dataType = "string")
    @NotBlank
    @Size(max = 20)
    String username;

    @ApiModelProperty(name = "password", dataType = "string")
    @NotBlank
    @Size(max = 20, min = 6)
    String password;

    @ApiModelProperty(name = "name", dataType = "string")
    @NotBlank
    @Size(max = 20)
    String name;

    @ApiModelProperty(name = "surname", dataType = "string")
    @Size(max = 50)
    String surname;

    @ApiModelProperty(name = "email", dataType = "string")
    @NotBlank(message = "Fill email")
    @Size(max = 50)
    @Email(message = "Invalid email format")
    String email;

    @ApiModelProperty(name = "organizationName", dataType = "string")
    @NotBlank
    @Size(max = 50)
    String organizationName;

    @ApiModelProperty(name = "organizationPhoneNumber", dataType = "string")
    @NotBlank
    @Size(max = 50)
    String organizationPhoneNumber;

    @ApiModelProperty(name = "organizationAddress", dataType = "string")
    @NotBlank
    @Size(max = 255)
    String organizationAddress;
}
