package com.fiap.postech.techchallenger.repository;

import com.fiap.postech.techchallenger.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String name);
    Optional<User> findByLogin(String login);
}
