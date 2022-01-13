package com.taskify.service;

import com.taskify.entity.User;
import com.taskify.model.user.UserAuthModel;
import com.taskify.model.user.UserRqModel;
import com.taskify.model.user.UserRsModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserAuthModel> findAuthModelByUsername(String username);
    Optional<UserAuthModel> findById(long id);
    UserRsModel addUser(UserRqModel userRqModel);
    List<UserRsModel> getUsers();

}
