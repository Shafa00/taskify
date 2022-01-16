package com.taskify.repository;

import com.taskify.entity.Organization;
import com.taskify.entity.Otp;
import com.taskify.entity.Role;
import com.taskify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndStatus(String email, String status);

    Optional<User> findByIdAndStatus(long id, String status);

    Optional<User> findByOtp(Otp otp);

    List<User> findAllByOrganization(Organization organization);

}
