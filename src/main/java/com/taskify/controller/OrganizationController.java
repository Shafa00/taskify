package com.taskify.controller;

import com.taskify.model.ResponseModel;
import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.organization.SignupRsModel;
import com.taskify.model.user.ConfirmOtpRqModel;
import com.taskify.model.user.ConfirmOtpRsModel;
import com.taskify.service.OrganizationService;
import com.taskify.service.OtpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RestController
@Validated
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Organization")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OtpService otpService;

    @ApiOperation("Sign up new organization and add admin user")
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseModel<SignupRsModel>> signUp(@Valid @RequestBody SignupRqModel signupRqModel) throws MessagingException {
        return ResponseEntity.ok(ResponseModel.of(organizationService.signup(signupRqModel), HttpStatus.CREATED));
    }

    @ApiOperation("Confirm otp of user")
    @PostMapping("/confirm-otp")
    public ResponseEntity<ResponseModel<ConfirmOtpRsModel>> confirmOtp(@Valid @RequestBody ConfirmOtpRqModel requestBody) {
        return ResponseEntity.ok(ResponseModel.of(otpService.confirmOtp(requestBody), OK));
    }

}
