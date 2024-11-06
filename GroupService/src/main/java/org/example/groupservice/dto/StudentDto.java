package org.example.groupservice.dto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDto
{
    private Integer student_id;
    private String surname;
    private String firstName;
    private String patronymic;
    private String gender;
    private LocalDate birthday;
    private String formOfEducation;
    private Integer numberOfFailedExams;

    private Integer groupId;
}
