package com.taskify.service;

import com.taskify.model.user.UserAuthModel;
import com.taskify.model.user.UserRqModel;
import com.taskify.model.user.UserRsModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserAuthModel> findAuthModelByEmail(String email);
    Optional<UserAuthModel> findById(long id);
    UserRsModel addUser(UserRqModel userRqModel, String adminEmail);
    List<UserRsModel> getUsersOfOrganization(String email);

}
