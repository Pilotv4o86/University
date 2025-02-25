package org.example.groupservice.dto;

import lombok.Data;


@Data
public class GroupResponse
{
    private Integer id;
    private String name;
    private Integer course;
    private String speciality;
    private String formOfEducation;
}
