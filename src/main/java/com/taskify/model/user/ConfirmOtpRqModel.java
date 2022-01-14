package com.taskify.model.user;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmOtpRqModel {
    @NotNull
    String email;

    @NotNull
    String otp;
}
