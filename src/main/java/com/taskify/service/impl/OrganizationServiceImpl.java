package com.taskify.service.impl;

import com.taskify.entity.Organization;
import com.taskify.entity.Otp;
import com.taskify.entity.Role;
import com.taskify.entity.User;
import com.taskify.exception.DataNotFoundException;
import com.taskify.exception.DuplicateUserException;
import com.taskify.mapper.OrganizationMapper;
import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.organization.SignupRsModel;
import com.taskify.repository.OrganizationRepository;
import com.taskify.repository.OtpRepository;
import com.taskify.repository.RoleRepository;
import com.taskify.repository.UserRepository;
import com.taskify.service.OrganizationService;
import com.taskify.utility.MailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import static com.taskify.utility.Constant.*;
import static com.taskify.utility.MessageConstant.DUPLICATE_USER_MSG;
import static java.lang.String.format;

@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final UserRepository userRepo;
    private final OrganizationRepository organizationRepo;
    private final OtpRepository otpRepo;
    private final OrganizationMapper organizationMapper;
    private final MailSenderService mailSenderService;
    private final BCryptPasswordEncoder encoder;
    private final RoleRepository roleRepo;

    @Override
    public SignupRsModel signup(SignupRqModel signupRqModel) throws MessagingException {
        checkUsernameAndEmailUniqueness(signupRqModel.getUsername(), signupRqModel.getEmail());

        Organization organization = organizationMapper.buildOrganization(signupRqModel);
        organizationRepo.save(organization);

        User adminUser = organizationMapper.buildUser(signupRqModel);
        adminUser.setOrganization(organization);
        adminUser.setPassword(encoder.encode(signupRqModel.getPassword()));

        adminUser.setRoles(Collections.singletonList(getRole()));

        userRepo.save(adminUser);

        String otp = generateOtp();
        otpRepo.save(Otp.builder()
                .otpId(UUID.randomUUID().toString())
                .otp(otp)
                .status(STATUS_NEW)
                .dateTime(LocalDateTime.now())
                .user(adminUser)
                .build());
        mailSenderService
                .sendEmail(signupRqModel.getEmail(), OTP_CONFIRMATION_SUBJECT,
                        format(OTP_CONFIRMATION_BODY, otp));

        return organizationMapper.buildSignUpRsModel(adminUser, organization);
    }

    private void checkUsernameAndEmailUniqueness(String username, String email) {
        if (userRepo.findByUsername(username).isPresent() && userRepo.findByEmail(email).isPresent())
            throw new DuplicateUserException(DUPLICATE_USER_MSG);
    }

    private String generateOtp() {
        return Integer.toString(new Random().nextInt(99999 - 10000) + 10000);
    }

    private Role getRole() {
        return roleRepo.findByName("ADMIN").orElseThrow(() -> new DataNotFoundException("Role not found"));
    }
}
