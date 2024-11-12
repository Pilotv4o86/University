package com.example.studentservice.repository;


import com.example.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer>
{
    void deleteAllByGroupId(Integer groupId);
    void deleteByIdAndGroupId(Integer id, Integer groupId);
    boolean existsByIdAndGroupId(Integer id, Integer groupId);
    Optional<Student> findAllByGroupId(Integer groupId);
    Optional<Student> findByIdAndGroupId(Integer id, Integer groupId);
}
