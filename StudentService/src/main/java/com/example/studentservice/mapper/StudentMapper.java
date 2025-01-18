package com.example.studentservice.mapper;

import com.example.studentservice.dto.StudentRequest;
import com.example.studentservice.dto.StudentResponse;
import com.example.studentservice.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public StudentResponse toStudentResponse(Student student) {
        return modelMapper.map(student, StudentResponse.class);
    }


    public Student toStudent(StudentRequest studentRequest) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper.map(studentRequest, Student.class);
    }

    public List<StudentResponse> toStudentResponseList(Optional<List<Student>> students) {
        return students
                .orElse(List.of()) // Если Optional пустой, возвращаем пустой список
                .stream()
                .map(this::toStudentResponse)
                .toList();
    }

    public void copyFields(Student student, StudentRequest studentRequest) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(studentRequest, student);
    }
}
