package com.example.viewservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRequest
{
    @NotBlank(message = "Требуется фамилия")
    private String surname;

    @NotBlank(message = "Требуется имя")
    private String firstName;

    @NotBlank(message = "Требуется отчество")
    private String patronymic;

    @NotBlank(message = "Требуется пол")
    @Pattern(regexp = "Мужской|Женский")
    private String gender;

    @NotNull(message = "Требуется дата рождения")
    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate birthday;

    @NotBlank(message = "Требуется форма образования")
    @Pattern(regexp = "Бюджет|Платно")
    private String formOfEducation;

    @NotNull(message = "Число несданных экзаменов")
    @Min(value = 0, message = "Число несданных экзаменов должно быть больше или равно 0")
    @Max(value = 2, message = "Число несданных экзаменов должно быть меньше или равно 2")
    private Integer numberOfFailedExams;
    private Long groupId;
}
