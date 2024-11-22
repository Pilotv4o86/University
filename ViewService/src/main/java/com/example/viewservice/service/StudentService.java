package com.example.viewservice.service;

import com.example.viewservice.client.StudentClient;
import com.example.viewservice.dto.StudentRequest;
import com.example.viewservice.dto.StudentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentClient studentClient;

    public StudentResponse create(Long groupId, StudentRequest studentRequest) {
        return studentClient.create(groupId, studentRequest);
    }

    public List<StudentResponse> getAllStudents(Long groupId) {
        return studentClient.allStudents(groupId);
    }

    public StudentResponse findByIdAndGroupId(Long id, Long groupId) {
        return studentClient.findByIdAndGroupId(id, groupId);
    }

    public StudentResponse update(Long id, Long groupId, StudentRequest studentRequest) {
        return studentClient.update(id, groupId, studentRequest);
    }

    public void deleteById(Long id, Long groupId) {
        studentClient.delete(id, groupId);
    }
}
