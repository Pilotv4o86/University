package org.example.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.userservice.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsUserByUsername(String username);
}
