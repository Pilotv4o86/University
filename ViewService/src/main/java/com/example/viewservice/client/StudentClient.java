package com.example.viewservice.client;

import com.example.viewservice.dto.student.StudentRequest;
import com.example.viewservice.dto.student.StudentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "StudentService")
public interface StudentClient {

    @PostMapping("/{groupName}/students/create")
    StudentResponse create(@PathVariable String groupName,
                           @RequestBody StudentRequest studentRequest);

    @GetMapping("/{groupName}/students/all-students")
    List<StudentResponse> allStudents(@PathVariable String groupName);

    @GetMapping("/{groupName}/students/{id}")
    StudentResponse getByIdAndGroupName(@PathVariable Long id,
                                        @PathVariable String groupName);

    @PutMapping("/{groupName}/students/{id}/update")
    StudentResponse update(@PathVariable Long id,
                           @PathVariable String groupName,
                           @RequestBody StudentRequest studentRequest);

    @DeleteMapping("/{groupId}/students/all-delete")
    void deleteAllStudents(@PathVariable Long groupId);

    @DeleteMapping("/{groupId}/students/{id}/delete")
    void delete(@PathVariable Long id,
                @PathVariable Long groupId);
}