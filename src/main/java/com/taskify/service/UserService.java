package com.taskify.service;

import com.taskify.model.user.UserRqModel;
import com.taskify.model.user.UserRsModel;

import java.util.List;

public interface UserService {
    UserRsModel addUser(UserRqModel userRqModel);
    List<UserRsModel> getUsers();

}
