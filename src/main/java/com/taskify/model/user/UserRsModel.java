package com.taskify.model.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRsModel {
    String userId;
    String name;
    String surname;
    String email;
    String organizationId;
}
