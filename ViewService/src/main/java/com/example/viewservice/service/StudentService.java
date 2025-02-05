package com.example.viewservice.service;

import com.example.viewservice.client.StudentClient;
import com.example.viewservice.dto.student.StudentRequest;
import com.example.viewservice.dto.student.StudentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentClient studentClient;

    public StudentResponse create(String groupName, StudentRequest studentRequest) {
        return studentClient.create(groupName, studentRequest);
    }

    public List<StudentResponse> getAllStudents(String groupName) {
        return studentClient.allStudents(groupName);
    }

    public StudentResponse getByIdAndGroupName(Long id, String groupName) {
        return studentClient.getByIdAndGroupName(id, groupName);
    }

    public StudentResponse update(Long id,
                                  String groupName,
                                  StudentRequest studentRequest) {
        return studentClient.update(id, groupName, studentRequest);
    }

    public void deleteById(Long id,
                           Long groupId) {
        studentClient.delete(id, groupId);
    }
}
