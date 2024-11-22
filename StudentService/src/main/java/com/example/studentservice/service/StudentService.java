package com.example.studentservice.service;

import com.example.studentservice.dto.StudentRequest;
import com.example.studentservice.dto.StudentResponse;
import com.example.studentservice.exception.StudentNotFoundException;
import com.example.studentservice.mapper.StudentMapper;
import com.example.studentservice.repository.StudentRepository;
import com.example.studentservice.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<StudentResponse> findAllByGroupId(Long groupId) {
        return studentMapper.toStudentResponseList(studentRepository.findAllByGroupId(groupId));
    }

    public StudentResponse create(Long groupId,
                                  StudentRequest studentRequest) {
        Student student = studentMapper.toStudent(studentRequest);
        student.setGroupId(groupId);
        student.setId(null);
        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    public StudentResponse update(Long id,
                                  StudentRequest studentRequest) {
        Student oldStudent = studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException("Student not found with id: " + id));

        studentMapper.copyFields(oldStudent, studentRequest);
        return studentMapper.toStudentResponse(studentRepository.save(oldStudent));
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }

        studentRepository.deleteById(id);
    }

    public void deleteAllByGroupId(Long groupId) {
        studentRepository.deleteAllByGroupId(groupId);
    }

    public StudentResponse getByIdAndGroupId(Long id, Long groupId) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException("Student not found with id: " + id));
        return studentMapper.toStudentResponse(student);
    }
}
