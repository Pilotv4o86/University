package com.example.viewservice.client;

import com.example.viewservice.dto.StudentRequest;
import com.example.viewservice.dto.StudentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "StudentService")
public interface StudentClient {

    @PostMapping("/{groupId}/students/create")
    StudentResponse create(@PathVariable Long groupId, @RequestBody StudentRequest studentRequest);

    @GetMapping("/{groupId}/students/all-students")
    List<StudentResponse> allStudents(@PathVariable Long groupId);

    @GetMapping("/{groupId}/students/{id}")
    StudentResponse findByIdAndGroupId(@PathVariable Long id, @PathVariable Long groupId);

    @PutMapping("/{groupId}/students/{id}/update")
    StudentResponse update(@PathVariable Long id, @PathVariable Long groupId, @RequestBody StudentRequest studentRequest);

    @DeleteMapping("/{groupId}/students/all-delete")
    void deleteAllStudents(@PathVariable Long groupId);

    @DeleteMapping("/{groupId}/students/{id}/delete")
    void delete(@PathVariable Long id, @PathVariable Long groupId);
}