package com.taskify.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmOtpRsModel {
    @ApiModelProperty(name = "email", dataType = "string")
    String email;

    @ApiModelProperty(name = "otpId", dataType = "string")
    String otpId;

    @ApiModelProperty(name = "otpStatus", dataType = "string")
    String otpStatus;

    @ApiModelProperty(name = "otpCreationDateTime", dataType = "string")
    String otpCreationDateTime;
}
