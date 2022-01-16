package com.taskify.service.impl;

import com.taskify.entity.User;
import com.taskify.exception.DataNotFoundException;
import com.taskify.exception.DuplicateUserException;
import com.taskify.model.user.UserAuthModel;
import com.taskify.model.user.UserRqModel;
import com.taskify.model.user.UserRsModel;
import com.taskify.repository.UserRepository;
import com.taskify.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.taskify.mapper.UserMapper.USER_MAPPER_INSTANCE;
import static com.taskify.utility.Constant.STATUS_ACTIVE;
import static com.taskify.utility.MessageConstant.*;

@Log4j2
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
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
    public UserRsModel addUser(UserRqModel userRqModel, String adminEmail) {
        User adminUser = getAdminUser(adminEmail);

        checkEmailUniqueness(userRqModel.getEmail());

        User user = USER_MAPPER_INSTANCE.buildUser(userRqModel);
        user.setPassword(encoder.encode(userRqModel.getPassword()));
        user.setOrganization(adminUser.getOrganization());
        userRepo.save(user);
        log.info(USER_CREATED_MSG, USER_MAPPER_INSTANCE.buildUserRsModel(user));

        return USER_MAPPER_INSTANCE.buildUserRsModel(user);
    }

    @Override
    public List<UserRsModel> getUsersOfOrganization(String email) {
        User adminUser = getAdminUser(email);
        return userRepo.findAllByOrganization(adminUser.getOrganization()).stream()
                .map(USER_MAPPER_INSTANCE::buildUserRsModel)
                .collect(Collectors.toList());
    }

    private void checkEmailUniqueness(String email) {
        if (userRepo.findByEmail(email).isPresent())
            throw new DuplicateUserException(DUPLICATE_USER_MSG);
    }

    private User getAdminUser(String adminEmail) {
        return userRepo.findByEmail(adminEmail).orElseThrow(
                () -> new DataNotFoundException(String.format(USER_NOT_FOUND_BY_EMAIL_MSG, adminEmail)));
    }

}
