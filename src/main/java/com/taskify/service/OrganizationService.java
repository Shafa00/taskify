package com.taskify.service;

import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.organization.SignupRsModel;

public interface OrganizationService {
    SignupRsModel signUp(SignupRqModel signUpRqModel);
}
