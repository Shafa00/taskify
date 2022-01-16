package com.taskify.model.user;

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
public class UserRqModel {
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
    @NotBlank
    @Size(max = 50)
    @Email(message = "email has invalid format")
    String email;
}
