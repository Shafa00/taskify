package com.taskify.controller;

import com.taskify.model.ResponseModel;
import com.taskify.model.user.UserRqModel;
import com.taskify.model.user.UserRsModel;
import com.taskify.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<ResponseModel<UserRsModel>> addUser(@RequestBody UserRqModel userRqModel) {
        return ResponseEntity.ok(ResponseModel.of(userService.addUser(userRqModel), HttpStatus.CREATED));
    }
}
