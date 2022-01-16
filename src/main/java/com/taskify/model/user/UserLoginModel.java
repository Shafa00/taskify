package com.taskify.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginModel {
    @ApiModelProperty(name = "email", dataType = "string")
    @NotBlank
    @Size(max = 20)
    @Email(message = "email has invalid format")
    String email;

    @ApiModelProperty(name = "password", dataType = "string")
    @NotBlank
    @Size(max = 20, min = 6)
    String password;
}