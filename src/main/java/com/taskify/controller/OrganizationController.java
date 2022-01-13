package com.taskify.controller;

import com.taskify.model.ResponseModel;
import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.organization.SignupRsModel;
import com.taskify.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseModel<SignupRsModel>> signUp(@RequestBody SignupRqModel signupRqModel) {
        return ResponseEntity.ok(ResponseModel.of(organizationService.signUp(signupRqModel), HttpStatus.CREATED));
    }
}
