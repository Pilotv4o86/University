package org.example.authservice.mapper;

import org.example.authservice.dto.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component

public class UserMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public UserResponse toStudentResponse(Student student)
    {
        return modelMapper.map(student, StudentResponse.class);
    }


    public Student toStudent(StudentRequest studentRequest)
    {
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

    public void copyFields(Student student, StudentRequest studentRequest)
    {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(studentRequest, student);
    }
}
