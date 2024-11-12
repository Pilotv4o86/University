package org.example.groupservice.service;

import lombok.AllArgsConstructor;
import org.example.groupservice.clients.StudentClient;
import org.example.groupservice.dto.StudentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService
{
    private StudentClient studentClient;
    public void deleteAllStudents(Integer groupId)
    {
        studentClient.deleteAllStudents(groupId);
    }

    public List<StudentResponse> getAllStudents(Integer groupId)
    {
        return studentClient.allStudents(groupId);
    }
}
