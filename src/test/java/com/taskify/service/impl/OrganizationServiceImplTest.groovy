package com.taskify.service.impl

import com.taskify.MockData
import com.taskify.entity.Organization
import com.taskify.exception.DataNotFoundException
import com.taskify.exception.DuplicateUserException
import com.taskify.repository.OrganizationRepository
import com.taskify.repository.OtpRepository
import com.taskify.repository.RoleRepository
import com.taskify.repository.UserRepository
import com.taskify.utility.MailSenderService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class OrganizationServiceImplTest extends Specification {

    private UserRepository userRepo
    private OrganizationRepository organizationRepo
    private OtpRepository otpRepo
    private RoleRepository roleRepo
    private MailSenderService mailSenderService
    private BCryptPasswordEncoder encoder
    private OrganizationServiceImpl organizationService

    void setup() {
        userRepo = Mock(UserRepository)
        organizationRepo = Mock(OrganizationRepository)
        otpRepo = Mock(OtpRepository)
        roleRepo = Mock(RoleRepository)
        mailSenderService = Mock(MailSenderService)
        encoder = Mock(BCryptPasswordEncoder)

        organizationService = new OrganizationServiceImpl(userRepo, organizationRepo, otpRepo, mailSenderService, encoder, roleRepo)
    }
    def "test successful signup"() {
        given:
        def singUpRequest = MockData.getValidSignupRqData()

        when:
        def signUpResponse = organizationService.signup(singUpRequest)

        then:
        1 * organizationRepo.save(_ as Organization)
        1 * userRepo.findByUsername("smamm") >> Optional.empty()
        1 * roleRepo.findByName("ROLE_ADMIN") >> MockData.getAdminRole()
        signUpResponse.getName() == "Shafa"
        signUpResponse.getEmail() == "shafamammadova@gmail.com"
    }

    def "test duplicate user"() {
        given:
        def signupRequest = MockData.validSignupRqData

        when:
        def response = organizationService.signup(signupRequest)

        then:
        userRepo.findByUsername("smamm") >> Optional.of(MockData.getUserData())
        userRepo.findByEmail("shafamammadova@gmail.com") >> Optional.of(MockData.getUserData())
        thrown DuplicateUserException
    }


    def "test role not found"() {
        given:
        def signupRequest = MockData.getValidSignupRqData()

        when:
        def signupResponse = organizationService.signup(signupRequest)

        then:
        1 * organizationRepo.save(_ as Organization)
        1 * userRepo.findByUsername("smamm") >> Optional.empty()
        1 * roleRepo.findByName("ROLE_ADMIN") >> Optional.empty()
        thrown DataNotFoundException

    }
}
