package com.taskify.model.user;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmOtpRqModel {
    @ApiModelProperty(name = "email", dataType = "string")
    @NotBlank
    @Size(max = 50)
    @Email(message = "email has invalid format")
    String email;

    @ApiModelProperty(name = "otp", dataType = "string")
    @NotBlank
    @Size(max = 5)
    String otp;
}
