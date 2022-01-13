package com.taskify.model.user;

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
public class UserRqModel {
    @NotNull
    String password;
    @NotNull
    String name;
    String surname;
    @NotNull
    String email;
}
