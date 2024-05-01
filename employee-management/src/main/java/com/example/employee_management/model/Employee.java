package com.example.employee_management.model;

import com.example.employee_management.enums.JobTitle;
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
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank
    @Column(name = "employee_name")
    private String name;

    @Column(name = "job_title")
    private JobTitle jobTitle;

    @Past
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Positive
    @Column(name = "salary")
    private double salary;

}
