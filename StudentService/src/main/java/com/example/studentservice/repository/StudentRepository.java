package com.example.studentservice.repository;


import com.example.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    void deleteAllByGroupId(Long groupId);
    void deleteByIdAndGroupId(Long id, Long groupId);
    boolean existsByIdAndGroupId(Long id, Long groupId);
    Optional<List<Student>> findAllByGroupId(Long groupId);

}
