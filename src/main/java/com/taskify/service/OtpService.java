package com.taskify.service;

import com.taskify.model.user.ConfirmOtpRqModel;
import com.taskify.model.user.ConfirmOtpRsModel;

public interface OtpService {
    ConfirmOtpRsModel confirmOtp(ConfirmOtpRqModel otpRequestModel);
}
