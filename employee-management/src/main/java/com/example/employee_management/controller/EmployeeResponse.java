package com.example.employee_management.controller;

import com.example.employee_management.enums.JobTitle;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class EmployeeResponse {

    private String name;

    private JobTitle jobTitle;

    private LocalDate dateOfBirth;

    private double salary;

    private String message;

    public EmployeeResponse(String message) {
        this.message = message;
    }

    public EmployeeResponse(String name, JobTitle jobTitle, LocalDate dateOfBirth, double salary) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
    }
}
