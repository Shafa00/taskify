package com.taskify.model.organization;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRsModel {
    String userId;
    String username;
    String name;
    String surname;
    String email;
    String organizationId;
    String organizationName;
    String organizationPhoneNumber;
    String organizationAddress;
}
