package com.example.employee_management.dto;

import com.example.employee_management.enums.JobTitles;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private long id;
    private String name;
    private JobTitles jobTitle;
    private LocalDate dateOfBirth;
    private double salary;
}
