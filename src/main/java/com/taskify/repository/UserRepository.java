package com.taskify.repository;

import com.taskify.entity.Otp;
import com.taskify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByOtp(Otp otp);

}
