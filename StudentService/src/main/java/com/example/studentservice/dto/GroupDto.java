package com.example.studentservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupDto
{
    private Integer id;
    private String name;
    private Integer course;
    private String speciality;
    private String formOfEducation;
}
