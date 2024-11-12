package com.example.studentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String surname;
    private String firstName;
    private String patronymic;
    private String gender;
    private LocalDate birthday;
    private String formOfEducation;
    private Integer numberOfFailedExams;
    private Integer groupId;

}
