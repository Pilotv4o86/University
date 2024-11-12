package com.example.studentservice.mapper;

import com.example.studentservice.dto.StudentDto;
import com.example.studentservice.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper
{
    private ModelMapper modelMapper = new ModelMapper();

    public StudentDto toDto(Student student)
    {
        return modelMapper.map(student, StudentDto.class);
    }

    public Student toStudent(StudentDto studentDto)
    {
        return modelMapper.map(studentDto, Student.class);
    }

    public void copyFields(Student student, StudentDto studentDto)
    {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(student, studentDto);
    }
}
