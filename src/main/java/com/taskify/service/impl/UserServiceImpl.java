package com.taskify.service.impl;

import com.taskify.model.user.UserAuthModel;
import com.taskify.model.user.UserRqModel;
import com.taskify.model.user.UserRsModel;
import com.taskify.repository.UserRepository;
import com.taskify.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.taskify.utility.Constant.STATUS_ACTIVE;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Override
    public Optional<UserAuthModel> findAuthModelByEmail(String email) {
        return userRepo.findByEmailAndStatus(email, STATUS_ACTIVE).map(UserAuthModel::new);
    }

    @Override
    public Optional<UserAuthModel> findById(long id) {
        return userRepo.findByIdAndStatus(id, STATUS_ACTIVE).map(UserAuthModel::new);
    }

    @Override
    public UserRsModel addUser(UserRqModel userRqModel) {
        //todo adduser method
        return null;
    }

    @Override
    public List<UserRsModel> getUsers() {
        //todo getUsers
        return null;
    }
}
