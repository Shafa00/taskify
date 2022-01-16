package com.taskify.controller;

import com.taskify.model.ResponseModel;
import com.taskify.model.user.UserLoginModel;
import com.taskify.model.user.UserRqModel;
import com.taskify.model.user.UserRsModel;
import com.taskify.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

import static com.taskify.utility.MessageConstant.RESPONSE_MSG;
import static com.taskify.utility.UrlConstant.*;

@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "User")
@Validated
@Log4j2
public class UserController {
    private final UserService userService;

    @ApiOperation("Login")
    @PostMapping("/login")
    public ResponseEntity<?> loginForDocumentation(@RequestBody UserLoginModel userLoginModel) {
        return ResponseEntity.ok("");
    }

    @ApiOperation("Add user")
    @PostMapping(ADD_USER_URL)
    public ResponseEntity<ResponseModel<UserRsModel>> addUser(@Valid @RequestBody UserRqModel userRqModel, Authentication authentication) {
        String adminEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        ResponseEntity<ResponseModel<UserRsModel>> response = ResponseEntity.ok(ResponseModel
                .of(userService.addUser(userRqModel, adminEmail), HttpStatus.CREATED));

        log.info(RESPONSE_MSG, ADD_USER_URL, response);
        return response;
    }

    @ApiOperation("Get users of organization")
    @GetMapping(GET_USERS_URL)
    public ResponseEntity<ResponseModel<List<UserRsModel>>> getUsers(Authentication auth) {
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        ResponseEntity<ResponseModel<List<UserRsModel>>> response = ResponseEntity.ok(ResponseModel
                .of(userService.getUsersOfOrganization(email), HttpStatus.OK));

        log.info(RESPONSE_MSG, GET_USERS_URL, response);
        return response;
    }
}
