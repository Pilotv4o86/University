package org.example.groupservice.service;

import lombok.AllArgsConstructor;
import org.example.groupservice.client.StudentClient;
import org.example.groupservice.dto.StudentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService
{
    private StudentClient studentClient;
    public void deleteAllStudents(Long groupId)
    {
        studentClient.deleteAllStudents(groupId);
    }

    public List<StudentResponse> getAllStudents(Long groupId)
    {
        return studentClient.allStudents(groupId);
    }
}
