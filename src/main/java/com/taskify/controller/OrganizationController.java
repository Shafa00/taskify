package com.taskify.controller;

import com.taskify.model.ResponseModel;
import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.organization.SignupRsModel;
import com.taskify.model.user.ConfirmOtpRqModel;
import com.taskify.model.user.ConfirmOtpRsModel;
import com.taskify.service.OrganizationService;
import com.taskify.service.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RestController
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OtpService otpService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseModel<SignupRsModel>> signUp(@RequestBody SignupRqModel signupRqModel) throws MessagingException {
        return ResponseEntity.ok(ResponseModel.of(organizationService.signup(signupRqModel), HttpStatus.CREATED));
    }

    @PostMapping("/confirm-otp")
    public ResponseEntity<ResponseModel<ConfirmOtpRsModel>> confirmOtp(@RequestBody ConfirmOtpRqModel requestBody) {
        return ResponseEntity.ok(ResponseModel.of(otpService.confirmOtp(requestBody), OK));
    }

}
