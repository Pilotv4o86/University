package com.example.viewservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupResponse
{
    private Long id;
    private String name;
    private Integer course;
    private String speciality;
    private String formOfEducation;
}
