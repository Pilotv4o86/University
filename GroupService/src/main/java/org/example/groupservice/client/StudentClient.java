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
    @DeleteMapping("/{groupName}/students/all-delete")
    void deleteAllStudents(@PathVariable("groupName") String groupName);
    @GetMapping("/{groupName}/students/all-students")
    List<StudentResponse> allStudents(@PathVariable("groupName") String groupName);
}
