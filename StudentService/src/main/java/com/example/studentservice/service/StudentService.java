package com.example.studentservice.service;

import com.example.studentservice.dto.StudentRequest;
import com.example.studentservice.dto.StudentResponse;
import com.example.studentservice.exception.StudentNotFoundException;
import com.example.studentservice.mapper.StudentMapper;
import com.example.studentservice.repository.StudentRepository;
import com.example.studentservice.model.Student;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<StudentResponse> findAllByGroupName(String groupName) {
        return studentMapper.toStudentResponseList(studentRepository.findAllByGroupName((groupName)));
    }

    public StudentResponse create(String groupName,
                                  StudentRequest studentRequest) {
        Student student = studentMapper.toStudent(studentRequest);
        student.setGroupName(groupName);
        student.setId(null);
        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    public StudentResponse update(Long id,
                                  StudentRequest studentRequest) {
        Student oldStudent = studentRepository.getReferenceById(id);

        studentMapper.copyFields(oldStudent, studentRequest);
        oldStudent.setId(id);
        return studentMapper.toStudentResponse(studentRepository.save(oldStudent));
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }

        studentRepository.deleteById(id);
    }

    public void deleteAllByGroupName(String groupName) {
        studentRepository.deleteAllByGroupName(groupName);
    }

    public StudentResponse getByIdAndGroupName(Long id, String groupName) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException("Student not found with id: " + id));
        return studentMapper.toStudentResponse(student);
    }
}
