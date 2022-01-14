package com.taskify.mapper;

import com.taskify.entity.Organization;
import com.taskify.entity.User;
import com.taskify.model.organization.SignupRqModel;
import com.taskify.model.organization.SignupRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public abstract class OrganizationMapper {

    public static OrganizationMapper ORGANIZATION_MAPPER_INSTANCE = Mappers.getMapper(OrganizationMapper.class);

    @Mappings({
            @Mapping(target = "name", source = "organizationName"),
            @Mapping(target = "phoneNumber", source = "organizationPhoneNumber"),
            @Mapping(target = "address", source = "organizationAddress")
    })
    public abstract Organization buildOrganization(SignupRqModel signupRqModel);

    @AfterMapping
    void setOrganizationId(@MappingTarget Organization.OrganizationBuilder organization) {
        organization.organizationId(UUID.randomUUID().toString());
    }

    public abstract User buildUser(SignupRqModel signupRqModel);

    @AfterMapping
    void setUserId(@MappingTarget User.UserBuilder user) {
        user.userId(UUID.randomUUID().toString());
    }

    @Mappings({
            @Mapping(target = "organizationName", source = "organization.name"),
            @Mapping(target = "organizationPhoneNumber", source = "organization.phoneNumber"),
            @Mapping(target = "organizationAddress", source = "organization.address"),
            @Mapping(target = "name", source = "user.name")
    })
    public abstract SignupRsModel buildSignUpRsModel(User user, Organization organization);


}
