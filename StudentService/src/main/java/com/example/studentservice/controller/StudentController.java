package com.example.studentservice.controller;

import com.example.studentservice.dto.StudentRequest;
import com.example.studentservice.dto.StudentResponse;
import com.example.studentservice.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/{groupName}/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/all-students")
    public ResponseEntity<List<StudentResponse>> getAllStudents(@PathVariable String groupName) {
        return ResponseEntity.ok(studentService.findAllByGroupName(groupName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentByIdAndGroupName(@PathVariable Long id,
                                                                      @PathVariable String groupName) {
        return ResponseEntity.ok(studentService.getByIdAndGroupName(id, groupName));
    }

    @PostMapping("/create")
    public ResponseEntity<StudentResponse> create(@PathVariable String groupName,
                                                  @RequestBody StudentRequest studentRequest) {
        StudentResponse savedStudent = studentService.create(groupName, studentRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedStudent.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedStudent);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id,
                                                  @RequestBody StudentRequest studentRequest) {
        return ResponseEntity.accepted().body(studentService.update(id, studentRequest));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all-delete")
    public ResponseEntity<Void> deleteAll(@PathVariable String groupName) {
        studentService.deleteAllByGroupName(groupName);
        return ResponseEntity.noContent().build();
    }
}
