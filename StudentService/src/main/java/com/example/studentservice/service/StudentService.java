package com.example.studentservice.service;

import com.example.studentservice.dto.StudentDto;
import com.example.studentservice.exception.StudentNotFoundException;
import com.example.studentservice.mapper.StudentMapper;
import com.example.studentservice.repository.StudentRepository;
import com.example.studentservice.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<StudentDto> findAllByGroupId(Integer groupId) {
        return studentRepository.findAllByGroupId(groupId)
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    public StudentDto create(Integer groupId,
                             StudentDto studentDto)
    {
        studentDto.setGroupId(groupId);
        Student student = studentMapper.toStudent(studentDto);
        return studentMapper.toDto(studentRepository.save(student));
    }

    public StudentDto update(Integer id, Integer groupId, StudentDto studentDto)
    {
        Student oldStudent = studentRepository.findByIdAndGroupId(id, groupId).orElseThrow(() ->
                new StudentNotFoundException("Student not found with id: " + id));

        studentMapper.copyFields(oldStudent, studentDto);
        return studentMapper.toDto(studentRepository.save(oldStudent));
    }

    public void delete(Integer id, Integer groupId)
    {
        if (!studentRepository.existsByIdAndGroupId(id,groupId))
        {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }

        studentRepository.deleteByIdAndGroupId(id, groupId);
    }

    public void deleteAllByGroupId(Integer groupId)
    {
        studentRepository.deleteAllByGroupId(groupId);
    }

    public StudentDto getById(Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException("Student not found with id: " + id));
        return studentMapper.toDto(student);
    }
}
