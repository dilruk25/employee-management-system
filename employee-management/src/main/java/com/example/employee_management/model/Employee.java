package com.example.employee_management.model;

import com.example.employee_management.enums.JobTitles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEES")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @NotBlank
    @Column(name = "EMPLOYEE_NAME")
    private String name;

    @NotBlank
    @Column(name = "JOB_TITLE")
    private JobTitles jobTitle;

    @Past
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;

    @Positive
    @Column(name = "SALARY")
    private double salary;

}
