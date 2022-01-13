package com.taskify.service.impl;

import com.taskify.entity.Organization;
import com.taskify.entity.User;
import com.taskify.exception.DuplicateUserException;
import com.taskify.mapper.OrganizationMapper;
import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.organization.SignupRsModel;
import com.taskify.repository.OrganizationRepository;
import com.taskify.repository.UserRepository;
import com.taskify.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.taskify.utility.MessageConstant.DUPLICATE_USER_MSG;

@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final UserRepository userRepo;
    private final OrganizationRepository organizationRepo;
    private final OrganizationMapper organizationMapper;

    @Override
    public SignupRsModel signUp(SignupRqModel signUpRqModel) {
        checkUsernameAndEmailUniqueness(signUpRqModel.getUsername(), signUpRqModel.getEmail());
        Organization organization = organizationMapper.buildOrganization(signUpRqModel);
        organizationRepo.save(organization);
        User adminUser = organizationMapper.buildUser(signUpRqModel);
        adminUser.setOrganization(organization);
        userRepo.save(adminUser);

        return organizationMapper.buildSignUpRsModel(adminUser, organization);
    }

    private void checkUsernameAndEmailUniqueness(String username, String email) {
        if (userRepo.findByUsername(username).isPresent() && userRepo.findByEmail(email).isPresent())
            throw new DuplicateUserException(DUPLICATE_USER_MSG);
    }
}
