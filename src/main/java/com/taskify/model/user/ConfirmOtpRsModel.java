package com.taskify.model.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmOtpRsModel {
    String email;
    String otpId;
    String otpStatus;
    LocalDateTime otpCreationDateTime;
}
