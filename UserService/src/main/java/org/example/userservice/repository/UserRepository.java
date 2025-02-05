package org.example.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.userservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsUserByUsername(String username);
}
