package com.taskify;

import com.taskify.entity.Organization;
import com.taskify.entity.Role;
import com.taskify.entity.User;
import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.user.ConfirmOtpRqModel;
import com.taskify.model.user.UserRqModel;

import java.util.Collections;
import java.util.Optional;

public class MockData {
    public static SignupRqModel getValidSignupRqData() {
        return SignupRqModel.builder()
                .email("shafamammadova@gmail.com")
                .name("Shafa")
                .organizationName("Organization")
                .organizationAddress("Baku, Azerbaijan")
                .organizationPhoneNumber("123456789")
                .password("123456")
                .username("smamm")
                .build();
    }

    public static ConfirmOtpRqModel getValidConfirmOtpRqData() {
        return ConfirmOtpRqModel.builder()
                .otp("12345")
                .email("shafamammadova@gmail.com")
                .build();
    }

    public static UserRqModel getValidUserRqData() {
        return UserRqModel.builder()
                .email("mammadova@gmail.com")
                .name("Shafa")
                .password("123456")
                .build();
    }

    public static Optional<Role> getAdminRole() {
        return Optional.of(Role.builder()
                .name("ROLE_ADMIN")
                .build());
    }

    public static User getUserData() {
        return User.builder()
                .userId("123")
                .email("shafamammadova@gmail.com")
                .name("Shafa")
                .password("123456")
                .username("smammadova")
                .organization(getOrganizationData())
                .roles(Collections.singletonList(getAdminRole().get()))
                .build();
    }

    public static Organization getOrganizationData() {
        return Organization.builder()
                .organizationId("123")
                .address("Baku, Azerbaijan")
                .name("Organization")
                .phoneNumber("123456789")
                .build();
    }


}
