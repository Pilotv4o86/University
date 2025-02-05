package com.example.viewservice.dto.group;

import lombok.Data;

@Data
public class GroupResponse
{
    private Long id;
    private String name;
    private Integer course;
    private String speciality;
    private String formOfEducation;
}
