package com.example.studentservice.repository;


import com.example.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    void deleteAllByGroupName(String groupName);
    Optional<List<Student>> findAllByGroupName(String groupName);
}
