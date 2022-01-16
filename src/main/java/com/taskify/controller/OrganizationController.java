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
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static com.taskify.utility.MessageConstant.RESPONSE_MSG;
import static com.taskify.utility.UrlConstant.CONFIRM_OTP_URL;
import static com.taskify.utility.UrlConstant.SIGNUP_URL;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RestController
@Validated
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Organization")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OtpService otpService;

    @ApiOperation("Sign up new organization and add admin user")
    @PostMapping(SIGNUP_URL)
    public ResponseEntity<ResponseModel<SignupRsModel>> signup(@Valid @RequestBody SignupRqModel signupRqModel) throws MessagingException {
        ResponseEntity<ResponseModel<SignupRsModel>> response = ResponseEntity.ok(ResponseModel
                .of(organizationService.signup(signupRqModel), HttpStatus.CREATED));

        log.info(RESPONSE_MSG, SIGNUP_URL, response);
        return response;
    }

    @ApiOperation("Confirm otp of user")
    @PostMapping(CONFIRM_OTP_URL)
    public ResponseEntity<ResponseModel<ConfirmOtpRsModel>> confirmOtp(@Valid @RequestBody ConfirmOtpRqModel requestBody) {
        ResponseEntity<ResponseModel<ConfirmOtpRsModel>> response = ResponseEntity.ok(ResponseModel
                .of(otpService.confirmOtp(requestBody), OK));

        log.info(RESPONSE_MSG, CONFIRM_OTP_URL, response);
        return response;
    }

}
