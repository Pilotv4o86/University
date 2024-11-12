package com.example.studentservice.controller;

import com.example.studentservice.dto.StudentDto;
import com.example.studentservice.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/{groupId}/students")
@AllArgsConstructor
public class StudentController
{
    private final StudentService studentService;

    @GetMapping("/all-students")
    public ResponseEntity<List<StudentDto>> getAllStudents(@PathVariable Integer groupId)
    {
        return ResponseEntity.ok(studentService.findAllByGroupId(groupId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Integer id, @PathVariable Integer groupId)
    {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<StudentDto> create(@PathVariable Integer groupId,
                                             @RequestBody StudentDto studentDto)
    {
        StudentDto savedStudent = studentService.create(groupId,studentDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedStudent.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedStudent);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<StudentDto> update(@PathVariable Integer id,
                                             @PathVariable Integer groupId,
                                             @RequestBody StudentDto studentDto)
    {
       return ResponseEntity.accepted().body(studentService.update(id,groupId,studentDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Integer id,
                                       @PathVariable Integer groupId)
    {
        studentService.delete(id,groupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAll(@PathVariable Integer groupId)
    {
        studentService.deleteAllByGroupId(groupId);
        return ResponseEntity.noContent().build();
    }
}
