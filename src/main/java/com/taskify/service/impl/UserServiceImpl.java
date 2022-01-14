package com.taskify.service.impl;

import com.taskify.entity.Role;
import com.taskify.entity.User;
import com.taskify.exception.DataNotFoundException;
import com.taskify.exception.DuplicateUserException;
import com.taskify.mapper.UserMapper;
import com.taskify.model.user.UserAuthModel;
import com.taskify.model.user.UserRqModel;
import com.taskify.model.user.UserRsModel;
import com.taskify.repository.RoleRepository;
import com.taskify.repository.UserRepository;
import com.taskify.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.taskify.utility.Constant.STATUS_ACTIVE;
import static com.taskify.utility.MessageConstant.DUPLICATE_USER_MSG;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;

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
        checkEmailUniqueness(userRqModel.getEmail());
        User user = UserMapper.USER_MAPPER_INSTANCE.buildUser(userRqModel);
        user.setPassword(encoder.encode(userRqModel.getPassword()));
        user.setRole(getRole());
        userRepo.save(user);

        // todo implement organization relation
        return userMapper.buildUserRsModel(user);
    }

    @Override
    public List<UserRsModel> getUsers() {
        //todo getUsers
        return null;
    }

    private void checkEmailUniqueness(String email) {
        if (userRepo.findByEmail(email).isPresent())
            throw new DuplicateUserException(DUPLICATE_USER_MSG);
    }

    private Role getRole() {
        return roleRepo.findByName("USER").orElseThrow(() -> new DataNotFoundException("Role not found"));
    }


}
