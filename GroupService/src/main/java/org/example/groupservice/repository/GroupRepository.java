package org.example.groupservice.repository;

import org.example.groupservice.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GroupRepository extends JpaRepository<Group, Long>
{
    Optional<Group> findByName(String name);

    void deleteByName(String name);

    Group getReferenceByName(String name);
}