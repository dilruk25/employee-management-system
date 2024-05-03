package com.example.employee_management.controller;

import com.example.employee_management.enums.JobTitle;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@Data
public class EmployeeRequest {
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private JobTitle jobTitle;

    private LocalDate dateOfBirth;

    private double salary;

    public EmployeeRequest(String name, String jobTitle, LocalDate dateOfBirth, double salary) {
        this.name = name;
        this.jobTitle = JobTitle.valueOf(jobTitle);
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
    }

}

