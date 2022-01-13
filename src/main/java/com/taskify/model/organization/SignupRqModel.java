package com.taskify.model.organization;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRqModel {
    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String name;
    String surname;
    @NotNull
    String email;
    @NotNull
    String organizationName;
    @NotNull
    String organizationPhoneNumber;
    @NotNull
    String organizationAddress;
}
