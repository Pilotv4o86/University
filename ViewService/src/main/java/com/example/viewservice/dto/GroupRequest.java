package com.example.viewservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class GroupRequest
{
    @NotBlank(message = "Требуется название группы")
    @Size(min = 1, max = 50, message = "Название группы должно содержать от 1 до 50 символов")
    private String name;

    @NotNull(message = "Требуется номер курса")
    @Min(value = 1, message = "Курс должен быть от 1")
    @Max(value = 4, message = "Курс должен быть до 4")
    private Integer course;

    @NotBlank(message = "Требуется название специальности")
    private String speciality;

    @NotBlank(message = "Требуется форма получения образования")
    @Pattern(regexp = "Очная|Заочная", message = "Форма образования должна быть очной либо заочной")
    private String formOfEducation;

}
