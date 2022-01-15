package com.taskify.service;

import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.organization.SignupRsModel;

import javax.mail.MessagingException;

public interface OrganizationService {
    SignupRsModel signup(SignupRqModel signUpRqModel) throws MessagingException;
}
