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
    public void deleteAllStudents(String groupName)
    {
        studentClient.deleteAllStudents(groupName);
    }

    public List<StudentResponse> getAllStudents(String groupName)
    {
        return studentClient.allStudents(groupName);
    }
}
