package com.taskify.mapper;

import com.taskify.entity.Organization;
import com.taskify.entity.User;
import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.organization.SignupRsModel;
import com.taskify.model.user.UserRqModel;
import com.taskify.model.user.UserRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public abstract class UserMapper {

    public static UserMapper USER_MAPPER_INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract User buildUser(UserRqModel userRqModel);

    @AfterMapping
    void setUserId(@MappingTarget User.UserBuilder user) {
        user.userId(UUID.randomUUID().toString());
    }

    public abstract UserRsModel buildUserRsModel(User user);


}
