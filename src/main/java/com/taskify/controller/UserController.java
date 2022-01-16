package com.taskify.controller;

import com.taskify.model.ResponseModel;
import com.taskify.model.user.UserRqModel;
import com.taskify.model.user.UserRsModel;
import com.taskify.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "User")
@Validated
public class UserController {
    private final UserService userService;

    @ApiOperation("Add user")
    @PostMapping("/add-user")
    public ResponseEntity<ResponseModel<UserRsModel>> addUser(@Valid @RequestBody UserRqModel userRqModel, Authentication authentication) {
        String adminEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        return ResponseEntity.ok(ResponseModel.of(userService.addUser(userRqModel, adminEmail), HttpStatus.CREATED));
    }

    @ApiOperation("Get users of organization")
    @GetMapping("/get-users")
    public ResponseEntity<ResponseModel<List<UserRsModel>>> getUsers(Authentication auth) {
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        return ResponseEntity.ok(ResponseModel.of(userService.getUsersOfOrganization(email), HttpStatus.OK));
    }
}
