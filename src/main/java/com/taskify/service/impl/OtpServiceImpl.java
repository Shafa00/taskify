package com.taskify.service.impl;

import com.taskify.entity.Otp;
import com.taskify.entity.User;
import com.taskify.exception.DataNotFoundException;
import com.taskify.exception.ExpiredOtpException;
import com.taskify.exception.InvalidOtpException;
import com.taskify.model.user.ConfirmOtpRqModel;
import com.taskify.model.user.ConfirmOtpRsModel;
import com.taskify.repository.OtpRepository;
import com.taskify.repository.UserRepository;
import com.taskify.service.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.taskify.mapper.OtpMapper.OTP_MAPPER_INSTANCE;
import static com.taskify.utility.Constant.*;
import static com.taskify.utility.MessageConstant.*;
import static java.lang.String.format;

@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepo;
    private final UserRepository userRepo;

    @Override
    public ConfirmOtpRsModel confirmOtp(ConfirmOtpRqModel otpRequestModel) {
        Otp otp = otpByValue(otpRequestModel);
        checkOtpAvailability(otp);

        if (otp.getUser().getEmail().equals(otpRequestModel.getEmail())) {
            User user = userByOtp(otp);
            updateOtpAndUserValues(otp, user);

            return OTP_MAPPER_INSTANCE.buildOtpResponseModel(otp, user);
        } else {
            throw new InvalidOtpException(INVALID_OTP_MSG);
        }
    }

    private Otp otpByValue(ConfirmOtpRqModel requestBody) {
        return otpRepo.findByOtp(requestBody.getOtp()).orElseThrow(
                () -> new InvalidOtpException(INVALID_OTP_MSG));
    }

    private User userByOtp(Otp otp) {
        return userRepo.findByOtp(otp).orElseThrow(
                () -> new DataNotFoundException(format(USER_BY_OTP_NOT_FOUND_MSG, otp.getOtp())));
    }

    private void updateOtpAndUserValues(Otp otp, User user) {
        otp.setStatus(STATUS_USED);
        otpRepo.save(otp);
        user.setStatus(STATUS_ACTIVE);
        userRepo.save(user);
    }

    private void checkOtpAvailability(Otp otp) {
        if (otp.getDateTime().isBefore(LocalDateTime.now().minusMinutes(2))
                || !otp.getStatus().equals(STATUS_NEW)) {
            throw new ExpiredOtpException(EXPIRED_OTP_MSG);
        }
    }

}
