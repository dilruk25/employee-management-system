package com.example.employee_management.controller;

import com.example.employee_management.enums.JobTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeResponse {

    private String name;

    private JobTitle jobTitle;

    private LocalDate dateOfBirth;

    private double salary;

}
