package com.quizgame.domain.user.repository;

import com.quizgame.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByName(String name);
    boolean existsByUserId(String userId);
}
