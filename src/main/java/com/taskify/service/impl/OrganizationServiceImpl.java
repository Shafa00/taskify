package com.taskify.service.impl;

import com.taskify.entity.Organization;
import com.taskify.entity.Otp;
import com.taskify.entity.Role;
import com.taskify.entity.User;
import com.taskify.exception.DataNotFoundException;
import com.taskify.exception.DuplicateUserException;
import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.organization.SignupRsModel;
import com.taskify.repository.OrganizationRepository;
import com.taskify.repository.OtpRepository;
import com.taskify.repository.RoleRepository;
import com.taskify.repository.UserRepository;
import com.taskify.service.OrganizationService;
import com.taskify.utility.MailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import static com.taskify.mapper.OrganizationMapper.ORGANIZATION_MAPPER_INSTANCE;
import static com.taskify.mapper.UserMapper.USER_MAPPER_INSTANCE;
import static com.taskify.utility.Constant.*;
import static com.taskify.utility.MessageConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final UserRepository userRepo;
    private final OrganizationRepository organizationRepo;
    private final OtpRepository otpRepo;
    private final MailSenderService mailSenderService;
    private final BCryptPasswordEncoder encoder;
    private final RoleRepository roleRepo;

    @Transactional
    @Override
    public SignupRsModel signup(SignupRqModel signupRqModel) throws MessagingException {
        checkUsernameAndEmailUniqueness(signupRqModel.getUsername(), signupRqModel.getEmail());

        Organization organization = ORGANIZATION_MAPPER_INSTANCE.buildOrganization(signupRqModel);
        organizationRepo.save(organization);
        log.info(ORGANIZATION_CREATED_MSG, organization.getOrganizationId());

        User adminUser = ORGANIZATION_MAPPER_INSTANCE.buildUser(signupRqModel);
        adminUser.setOrganization(organization);
        adminUser.setPassword(encoder.encode(signupRqModel.getPassword()));
        adminUser.setRoles(Collections.singletonList(getRole()));
        userRepo.save(adminUser);
        log.info(USER_CREATED_MSG, USER_MAPPER_INSTANCE.buildUserRsModel(adminUser));

        String otp = generateOtp();
        otpRepo.save(buildOtp(otp, adminUser));
        log.info(OTP_CREATED_MSG, otp);

        mailSenderService
                .sendEmail(signupRqModel.getEmail(), OTP_CONFIRMATION_SUBJECT,
                        format(OTP_CONFIRMATION_BODY, otp));

        return ORGANIZATION_MAPPER_INSTANCE.buildSignUpRsModel(adminUser, organization);
    }

    private void checkUsernameAndEmailUniqueness(String username, String email) {
        if (userRepo.findByUsername(username).isPresent() && userRepo.findByEmail(email).isPresent())
            throw new DuplicateUserException(DUPLICATE_USER_MSG);
    }

    private String generateOtp() {
        return Integer.toString(new Random().nextInt(99999 - 10000) + 10000);
    }

    private Otp buildOtp(String otp, User user) {
        return Otp.builder()
                .otpId(UUID.randomUUID().toString())
                .otp(otp)
                .status(STATUS_NEW)
                .dateTime(LocalDateTime.now())
                .user(user)
                .build();
    }

    private Role getRole() {
        return roleRepo.findByName("ROLE_ADMIN").orElseThrow(
                () -> new DataNotFoundException(ROLE_NOT_FOUND_MSG));
    }
}
