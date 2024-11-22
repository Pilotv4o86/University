package com.example.studentservice.controller;

import com.example.studentservice.dto.StudentRequest;
import com.example.studentservice.dto.StudentResponse;
import com.example.studentservice.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/{groupId}/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/all-students")
    public ResponseEntity<List<StudentResponse>> getAllStudents(@PathVariable Long groupId) {
        return ResponseEntity.ok(studentService.findAllByGroupId(groupId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentByIdAndGroupId(@PathVariable Long id,
                                                                    @PathVariable Long groupId) {
        return ResponseEntity.ok(studentService.getByIdAndGroupId(id, groupId));
    }

    @PostMapping("/create")
    public ResponseEntity<StudentResponse> create(@PathVariable Long groupId,
                                                  @RequestBody StudentRequest studentRequest) {
        StudentResponse savedStudent = studentService.create(groupId, studentRequest);
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
    public ResponseEntity<Void> deleteAll(@PathVariable Long groupId) {
        studentService.deleteAllByGroupId(groupId);
        return ResponseEntity.noContent().build();
    }
}
