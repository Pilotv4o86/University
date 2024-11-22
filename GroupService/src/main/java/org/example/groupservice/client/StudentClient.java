package org.example.groupservice.client;

import org.example.groupservice.dto.StudentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "StudentService")
public interface StudentClient
{
    @DeleteMapping("/{groupId}/students/all-delete")
    void deleteAllStudents(@PathVariable("groupId") Long groupId);
    @GetMapping("/{groupId}/students/all-students")
    List<StudentResponse> allStudents(@PathVariable("groupId") Long groupId);
}
